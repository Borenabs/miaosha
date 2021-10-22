package com.lan.miaosha.result;

import lombok.Data;

import java.util.concurrent.CountDownLatch;

@Data
public class CodeMsg {
    private int code;
    private String msg;

    //default success
    static final int SUCCESS_CODE = 0;
    static  final String SUCCESS_MSG = "SUCCESS";

    //default error
    static final int DEFAULT_ERROR_CODE = 500;
    static final int INVENTORY_SHORTAGE_CODE = 5005;
    static final int DUPLICATE_MIAOSHA_CODE = 5006;
    static final String DEFAULT_ERROR_MSG = "Server Error";

    //login
    static final String PASSWORD_ERROR_MSG = "Password Error";
    static final String MOBILE_NOT_EXISTS_MSG = "Mobile not exists";
    static final String INVENTORY_SHORTAGE_MSG = "inventory shortage";
    static final String DUPLICATE_MIAOSHA_MSG = "duplicate miaosha";

    //Default Code;
    public static CodeMsg SUCCESS = new CodeMsg(SUCCESS_CODE , SUCCESS_MSG);
    public static CodeMsg DEFAULT_ERROR = new CodeMsg(DEFAULT_ERROR_CODE , DEFAULT_ERROR_MSG);
    //Validation Code
    public static CodeMsg BIND_ERROR = new CodeMsg(DEFAULT_ERROR_CODE , "参数校验错误： S%");
    //special Code;
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(DEFAULT_ERROR_CODE , PASSWORD_ERROR_MSG);
    public static CodeMsg MOBILE_NOT_EXISTS = new CodeMsg(DEFAULT_ERROR_CODE , MOBILE_NOT_EXISTS_MSG);


    //goods Code
    public static CodeMsg INVENTORY_SHORTAGE  = new CodeMsg(INVENTORY_SHORTAGE_CODE , INVENTORY_SHORTAGE_MSG);
    public static CodeMsg DUPLICATE_MIAOSHA  = new CodeMsg(DUPLICATE_MIAOSHA_CODE , DUPLICATE_MIAOSHA_MSG);


    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg , args);
        return new CodeMsg(code , message);
    }

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
