package com.lan.miaosha.controller;

import com.lan.miaosha.result.Result;
import com.lan.miaosha.service.MiaoshaUserService;
import com.lan.miaosha.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

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
    public Result<Boolean> login(LoginVO loginVO , HttpServletResponse response){
        miaoshaUserService.login(loginVO ,response);
        return Result.success(true);
    }

}
