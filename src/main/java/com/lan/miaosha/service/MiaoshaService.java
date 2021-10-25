package com.lan.miaosha.service;

import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.domain.OrderInfo;
import com.lan.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goods) {
        goodsService.reduceStock(goods);
        return orderService.createOrder(miaoshaUser , goods);
    }
}
