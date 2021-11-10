package com.lan.miaosha.controller;

import com.lan.miaosha.domain.Goods;
import com.lan.miaosha.domain.MiaoshaOrder;
import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.domain.OrderInfo;
import com.lan.miaosha.rabbitmq.MQSender;
import com.lan.miaosha.rabbitmq.MiaoshaMessage;
import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.redis.key.GoodsKey;
import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.result.Result;
import com.lan.miaosha.service.GoodsService;
import com.lan.miaosha.service.MiaoshaService;
import com.lan.miaosha.service.OrderService;
import com.lan.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MiaoshaController implements InitializingBean {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

    /**
     *
     * @param model
     * @param miaoshaUser
     * @param goodsId
     * @return
     * get/post有什么区别: get是幂等的 ， 对服务端获取数据，不能改变服务端的数据
     * post : 向服务端提交数据 。
     */
//    @PostMapping("/miaosha/do_miaosha")
//    @ResponseBody
//    public Result<OrderInfo> miaosha(Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId") Long goodsId){
//        //validate user
//        if(miaoshaUser==null) return Result.error(CodeMsg.USER_SESSION_INVALID);
//        //validate goods stock
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        if(goods.getStockCount() <= 0) return Result.error(CodeMsg.INVENTORY_SHORTAGE);
//        //validate duplicate miaosha
//        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrder(miaoshaUser.getId() , goodsId);
//        if(miaoshaOrder != null) return Result.error(CodeMsg.DUPLICATE_MIAOSHA);
//        //减库存 ，下订单 ， 写入秒杀订单
//        OrderInfo orderInfo = miaoshaService.miaosha(miaoshaUser , goods);
//        model.addAttribute("orderInfo" , orderInfo);
//        model.addAttribute("goods" ,goods);
//        return Result.success(orderInfo);
//    }

    @PostMapping("/miaosha/do_miaosha")
    @ResponseBody
    public Result<Integer> miaosha(Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId") Long goodsId){
        //减库存
        Long inventory = redisService.decr(GoodsKey.goodsInventory, "" + goodsId);
        if(inventory < 0) return Result.error(CodeMsg.INVENTORY_SHORTAGE);

        //判断重复秒杀
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrder(miaoshaUser.getId()+"_"+goodsId);
        if(miaoshaOrder!=null) return Result.error(CodeMsg.DUPLICATE_MIAOSHA);

        //减库存成功，入队
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setGoodsId(goodsId);
        miaoshaMessage.setMiaoshaUser(miaoshaUser);
        sender.sendMiaoshaMessage(miaoshaMessage);

        return Result.success(0); //0 表啥排队中
    }


    @GetMapping("/miaosha/result")
    @ResponseBody
    public Result<Integer> getMiaoshaResult(MiaoshaUser miaoshaUser , @RequestParam("goodsId") long goodsId){
        String miaoshaKey = miaoshaUser.getId()+"_"+goodsId;
        MiaoshaOrder miaoshaOrder =  orderService.getMiaoshaOrder(miaoshaKey);
        if(miaoshaOrder == null){
            if(miaoshaService.isMiaoshaOver(String.valueOf(goodsId))){
                return Result.error(CodeMsg.INVENTORY_SHORTAGE);
            }
            return Result.error(CodeMsg.IN_LINE);
        }
        return Result.success(0);
    }

    /**
     * 系统初始化加载
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if(goodsList == null) return ;
        for (GoodsVo goodsVo : goodsList) {
            redisService.set(GoodsKey.goodsInventory,goodsVo.getId()+"" , goodsVo);
        }

    }
}
