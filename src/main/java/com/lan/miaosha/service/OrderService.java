package com.lan.miaosha.service;

import com.lan.miaosha.domain.MiaoshaOrder;
import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.domain.OrderInfo;
import com.lan.miaosha.mapper.OrderMapper;
import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.redis.key.OrderKey;
import com.lan.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RedisService redisService;


    public OrderInfo getOrderById(Long orderId) {
        return orderMapper.getOrderById(orderId);
    }

    /**
     * get miaoshaOrder from db
     * @param userId
     * @param goodsId
     * @return
     */
    public MiaoshaOrder getMiaoshaOrder(Long userId, Long goodsId) {
        return  orderMapper.getMiaoshaOrder(userId,goodsId);
    }

    /**
     * get miaoshaOrder from redis
     * @param miaoshaKey
     * @return
     */
    public MiaoshaOrder getMiaoshaOrder(String miaoshaKey){
        return redisService.get(OrderKey.miaoshaOrderKey , miaoshaKey , MiaoshaOrder.class);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser miaoshaUser, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(miaoshaUser.getId());
        orderMapper.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(orderInfo.getGoodsId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(miaoshaUser.getId());
        orderMapper.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.miaoshaOrderKey , miaoshaUser.getId()+"_"+goods.getId() , miaoshaOrder);

        return orderInfo;
    }

}
