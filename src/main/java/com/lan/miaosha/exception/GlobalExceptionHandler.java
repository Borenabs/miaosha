package com.lan.miaosha.exception;

import com.lan.miaosha.result.CodeMsg;
import com.lan.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request , Exception e){
        if(e instanceof GlobalException){
            e.printStackTrace();
            GlobalException globalException = (GlobalException) e;
            return  Result.error(globalException.getCodeMsg());
        }else if(e instanceof BindException){
            BindException bindException = (BindException) e;
            List<ObjectError> allErrors =  bindException.getAllErrors();
            ObjectError objectError = allErrors.get(0);
            String msg = objectError.getDefaultMessage();
           return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else{
            return Result.error(CodeMsg.DEFAULT_ERROR);
        }
    }
}
