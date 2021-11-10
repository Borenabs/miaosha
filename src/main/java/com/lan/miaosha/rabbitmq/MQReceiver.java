package com.lan.miaosha.rabbitmq;

import com.lan.miaosha.domain.MiaoshaOrder;
import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.domain.OrderInfo;
import com.lan.miaosha.exception.GlobalException;
import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.result.Result;
import com.lan.miaosha.service.GoodsService;
import com.lan.miaosha.service.MiaoshaService;
import com.lan.miaosha.service.OrderService;
import com.lan.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    GoodsService goodsService;

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    /*@RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String msg){
        logger.info("receive msg :" + msg);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE_1)
    public void receiveTopic_1(String msg){
        logger.info("receive topic msg" + msg);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE_2)
    public void receiveTopic_2(String msg){
        logger.info("receive topic msg" + msg);
    }*/

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaoshaMessage(String  message){
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        long goodsId = miaoshaMessage.getGoodsId();
        MiaoshaUser miaoshaUser = miaoshaMessage.getMiaoshaUser();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        //判断库存
        int goodsInventory  = goods.getStockCount();
        if(goodsInventory <= 0){
            throw new GlobalException(CodeMsg.INVENTORY_SHORTAGE);
        }
        //判断重复秒杀
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrder(miaoshaUser.getId() , goodsId);
        if(miaoshaOrder != null)  throw new GlobalException(CodeMsg.DUPLICATE_MIAOSHA);

        //
        OrderInfo order = miaoshaService.miaosha(miaoshaUser , goods);
        //减库存
        goodsService.reduceStock(goods);

    }
}
