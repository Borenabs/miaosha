package com.lan.miaosha.controller;

import com.lan.miaosha.domain.MiaoshaGoods;
import com.lan.miaosha.domain.MiaoshaOrder;
import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.domain.OrderInfo;
import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.service.GoodsService;
import com.lan.miaosha.service.MiaoshaService;
import com.lan.miaosha.service.OrderService;
import com.lan.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;
    
    @RequestMapping("/miaosha/do_miaosha")
    public String miaosha(Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId") Long goodsId){
        //validate goods stock
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        if(goods.getStockCount() <= 0){
            model.addAttribute("errormsg" , CodeMsg.INVENTORY_SHORTAGE.getMsg());
            return "miaosha_fail";
        }
        //validate duplicate miaosha
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrder(miaoshaUser.getId() , goodsId);
        if(miaoshaOrder != null) {
            model.addAttribute("errormsg" , CodeMsg.DUPLICATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存 ，下订单 ， 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(miaoshaUser , goods);
        model.addAttribute("orderInfo" , orderInfo);
        model.addAttribute("goods" ,goods);
        return "order_detail";
    }
}
