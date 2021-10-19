package com.lan.miaosha.controller;

import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.redis.key.MiaoshaUserKey;
import com.lan.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class GoodsController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_goodsList")
    public String goodsPage(Model model , HttpServletResponse response, MiaoshaUser miaoshaUser,
                            @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN , required = false) String cookieToken ,
                            @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN , required = false) String paramToken){
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) return null;
////        String token = StringUtils.isEmpty(cookieToken)? paramToken:cookieToken;
////
////        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(response ,token);

        model.addAttribute("user" , miaoshaUser);

        return "goods_list";
    }
}
