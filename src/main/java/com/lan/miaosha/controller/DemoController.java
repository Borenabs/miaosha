package com.lan.miaosha.controller;

import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.redis.key.UserKey;
import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    RedisService redisService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/codeMsg")
    public Result<String> codeMsg(){
        return Result.error(new CodeMsg(404 , "NOT FOUND"));
    }

    @RequestMapping("/redisTest")
    public Result<UserKey> redisGetTest(){
        return Result.success(redisService.get(UserKey.getByiId,"key1" , UserKey.class));
    }

    @RequestMapping("/redisSetTest")
    public Result<UserKey> redisSetTest(){
       return Result.success(redisService.set(UserKey.getByiId,"key2" , UserKey.getByiId));
    }
}
