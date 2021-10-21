package com.lan.miaosha.controller;

import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.redis.key.MiaoshaUserKey;
import com.lan.miaosha.service.GoodsService;
import com.lan.miaosha.service.MiaoshaUserService;
import com.lan.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class GoodsController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_goodsList")
    public String goodsPage(Model model , HttpServletResponse response, MiaoshaUser miaoshaUser,
                            @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN , required = false) String cookieToken ,
                            @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN , required = false) String paramToken){
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) return null;
////        String token = StringUtils.isEmpty(cookieToken)? paramToken:cookieToken;
////
////        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(response ,token);

        model.addAttribute("user" , miaoshaUser);
        List<GoodsVo> goods =  goodsService.listGoodsVo();
        model.addAttribute("goodsList", goods);
        return "goods_list";
    }

    @RequestMapping("/goods/to_detail/{goodId}")
    public String goodDetailPage(@PathVariable String goodId , Model model , MiaoshaUser miaoshaUser){
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(Long.valueOf(goodId));
        model.addAttribute("goods" , goods);
        model.addAttribute("user" , miaoshaUser);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goods_detail";
    }
}
