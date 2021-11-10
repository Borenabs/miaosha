package com.lan.miaosha.controller;

import com.lan.miaosha.domain.Goods;
import com.lan.miaosha.domain.MiaoshaOrder;
import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.domain.OrderInfo;
import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.result.Result;
import com.lan.miaosha.service.GoodsService;
import com.lan.miaosha.service.OrderService;
import com.lan.miaosha.vo.GoodsVo;
import com.lan.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    public Result<OrderDetailVo> getOrderInfo(MiaoshaUser miaoshaUser , @RequestParam("orderId") Long orderId){
        if (miaoshaUser == null) return Result.error(CodeMsg.USER_SESSION_INVALID);

        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if (orderInfo == null) return Result.error(CodeMsg.ORDER_NOT_EXISTS);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(orderInfo.getGoodsId());

        OrderDetailVo orderDetail = new OrderDetailVo();
        orderDetail.setGoods(goods);
        orderDetail.setOrderInfo(orderInfo);
        return Result.success(orderDetail);
    }
}
