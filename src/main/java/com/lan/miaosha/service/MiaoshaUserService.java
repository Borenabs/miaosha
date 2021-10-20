package com.lan.miaosha.service;

import com.lan.miaosha.domain.MiaoshaUser;
import com.lan.miaosha.exception.GlobalException;
import com.lan.miaosha.mapper.MiaoshaUserMapper;
import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.redis.key.MiaoshaUserKey;
import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.util.MD5Util;
import com.lan.miaosha.util.UUIdUtil;
import com.lan.miaosha.vo.LoginVO;
import groovy.grape.GrapeIvy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getByToken(HttpServletResponse response,String token) {
        if (StringUtils.isEmpty(token)) return null;
        //延长session有效期
        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token , token , MiaoshaUser.class);
        if(miaoshaUser != null){
            addCookie(response , miaoshaUser);
        }
        return miaoshaUser;
    }

    public MiaoshaUser selectById(Long userId){
        return miaoshaUserMapper.selectById(userId);
    }

    public Boolean login(@Valid LoginVO loginVO , HttpServletResponse response){
        if(loginVO == null){
            throw new GlobalException(CodeMsg.DEFAULT_ERROR);
        }
        String password = loginVO.getPassword();
        String mobile = loginVO.getMobile();
        MiaoshaUser miaoshaUser = this.selectById(Long.valueOf(mobile));
        if(!mobile.equals(String.valueOf(miaoshaUser.getId()))){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXISTS);
        }
        if (!MD5Util.formPassToDBPass(password , miaoshaUser.getSalt()).equals(miaoshaUser.getPassword())){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        addCookie(response , miaoshaUser);
        return true;
    }

    private void addCookie(HttpServletResponse response , MiaoshaUser miaoshaUser){
        //登录成功之后 ， 生成一个Cookie
        String token = UUIdUtil.uuid();
        //将token存到redis中
        redisService.set(MiaoshaUserKey.token , token , miaoshaUser);

        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN , token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSecond());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
