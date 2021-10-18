package com.lan.miaosha.controller;

import com.lan.miaosha.domain.MiaoShaUser;
import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.result.Result;
import com.lan.miaosha.service.MiaoshaUserService;
import com.lan.miaosha.util.MD5Util;
import com.lan.miaosha.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<CodeMsg> login(@Valid LoginVO loginVO){
        String password = loginVO.getPassword();
        String mobile = loginVO.getMobile();

        MiaoShaUser miaoshaUser = miaoshaUserService.selectById(Long.valueOf(mobile));

        if (MD5Util.formPassToDBPass(password , miaoshaUser.getSalt()).equals(miaoshaUser.getPassword())){
            return  Result.success(CodeMsg.SUCCESS);
        }
        return Result.error(CodeMsg.DEFAULT_ERROR);
    }

}
