package com.lan.miaosha.service;

import com.lan.miaosha.domain.Goods;
import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.domain.OrderInfo;
import com.lan.miaosha.exception.GlobalException;
import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.redis.key.GoodsKey;
import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.vo.GoodsVo;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goods) {
        boolean reduceSuccess =  goodsService.reduceStock(goods);
        if (!reduceSuccess) {
            setMiaoshaOver(String.valueOf(goods.getId()));
        }
        return orderService.createOrder(miaoshaUser, goods);
    }

    public void setMiaoshaOver(String goodsId){
        redisService.set(GoodsKey.goodsMiaoshaOver, goodsId , "miaoshaOver");
    }
    public boolean isMiaoshaOver(String goodsId){
        return redisService.exists(GoodsKey.goodsMiaoshaOver , goodsId);
    }
}
