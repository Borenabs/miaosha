package com.lan.miaosha.result;

import com.sun.org.apache.bcel.internal.classfile.Code;
import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data) {
        this.code = CodeMsg.SUCCESS_CODE;
        this.msg = CodeMsg.SUCCESS_MSG;
        this.data = data;
    }

    private Result(CodeMsg codeMsg){
        if(codeMsg == null) return;
        this.msg = codeMsg.getMsg();
        this.code = codeMsg.getCode();
    }
    //success
    public static <T> Result<T> success(T data){
        return new  Result<T>(data);
    }

    //error
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new  Result<T>(codeMsg);
    }
}
