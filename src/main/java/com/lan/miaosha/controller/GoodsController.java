package com.lan.miaosha.controller;

import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.redis.key.GoodsKey;
import com.lan.miaosha.redis.key.MiaoshaUserKey;
import com.lan.miaosha.result.Result;
import com.lan.miaosha.service.GoodsService;
import com.lan.miaosha.service.MiaoshaUserService;
import com.lan.miaosha.vo.GoodsDetailVo;
import com.lan.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

@Controller
public class GoodsController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_goodsList" , produces = "text/html")
    @ResponseBody
    public String goodsPage(Model model , HttpServletResponse response, MiaoshaUser miaoshaUser, HttpServletRequest request,
                            @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN , required = false) String cookieToken ,
                            @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN , required = false) String paramToken){
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) return null;
////        String token = StringUtils.isEmpty(cookieToken)? paramToken:cookieToken;
////
////        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(response ,token);
        //获取缓存
//        public SpringWebContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, Locale locale, Map<String, ?> variables, ApplicationContext
//        appctx) {
//            super(request, response, servletContext, locale, addSpringSpecificVariables(variables, appctx));
//            this.applicationContext = appctx;
//        }
        //获取缓存
        String html = redisService.get(GoodsKey.goodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        //如果缓存为空
        model.addAttribute("user" , miaoshaUser);
        List<GoodsVo> goods =  goodsService.listGoodsVo();
        model.addAttribute("goodsList", goods);

        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        String goodsListHtml = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
        if(!StringUtils.isEmpty(goodsListHtml)){
            redisService.set(GoodsKey.goodsList ,"",goodsListHtml);
        }
        return goodsListHtml;

    }

    @RequestMapping(value = "/goods/to_detail/{goodId}" , produces = "text/html")
    @ResponseBody
    public String goodDetailPage(@PathVariable String goodId , Model model , MiaoshaUser miaoshaUser , HttpServletRequest request ,HttpServletResponse response){
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

        //获取缓存
        String html = redisService.get(GoodsKey.goodsDetail , goodId , String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        String goodsListHtml = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);

        if(!StringUtils.isEmpty(goodsListHtml)){
            redisService.set(GoodsKey.goodsList ,":"+goodId , goodsListHtml);
        }
        return goodsListHtml;
    }

    @GetMapping("/goods/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> goodsDetail(@PathVariable Long goodsId , MiaoshaUser miaoshaUser){
        //获取缓存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
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

        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goods);
        goodsDetailVo.setUser(miaoshaUser);
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemainSeconds(remainSeconds);

        return Result.success(goodsDetailVo);
    }
}
