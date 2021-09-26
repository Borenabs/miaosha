package com.lan.miaosha.controller;

import com.lan.miaosha.redis.RedisService;
import com.lan.miaosha.domain.Test;
import com.lan.miaosha.redis.UserKey;
import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.result.Result;
import com.lan.miaosha.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    TestService testService;

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

    @RequestMapping("/testSQL")
    public Result<List<Test>> testSql(){
        return Result.success(testService.testList());
    }

    @RequestMapping("/testTransaction")
    @Transactional
    public int testTracnsaction(){
        Test test_1 = new Test().setUsername("陈永仁").setNumber(02).setPasswd("123546789");
        Test test_2 = new Test().setUsername("刘建明").setNumber(02).setPasswd("123456");
        testService.insertTest(test_1);
        testService.insertTest(test_2);
        return 2;
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
