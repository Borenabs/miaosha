package com.lan.miaosha.exception;

import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request , Exception e){

        if(e instanceof RuntimeException){
           GlobalException globalException = (GlobalException) e;
           globalException

           String msg = "";

           return Result.error(CodeMsg.BIND_ERROR.fillArgs())
        }
    }
}
