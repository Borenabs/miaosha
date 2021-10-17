package com.lan.miaosha.result;

import lombok.Data;

import java.util.concurrent.CountDownLatch;

@Data
public class CodeMsg {
    private int code;
    private String msg;

    static final int SUCCESS_CODE = 0;
    static  final String SUCCESS_MSG = "SUCCESS";

    static final int DEFAULT_ERROR_CODE = 500;
    static  final String DEFAULT_ERROR_MSG = "Server Error";

    //Default Code;
    public static CodeMsg SUCCESS = new CodeMsg(SUCCESS_CODE , SUCCESS_MSG);
    public static CodeMsg DEFAULT_ERROR = new CodeMsg(DEFAULT_ERROR_CODE , DEFAULT_ERROR_MSG);

    //special Code;


    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
