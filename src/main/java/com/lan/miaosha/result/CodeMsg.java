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
    static final int USER_NOT_FOUND_CODE = 5007;
    static final String USER_NOT_FOUND_MSG = "user not found";
    static final int USER_SESSION_INVALID_CODE = 5008;
    static final String USER_SESSION_INVALID_MSG = "user session invalid";
    static final int ORDER_NOT_EXISTS_CODE = 5009;
    static final String ORDER_NOT_EXISTS_MSG = "order not exists";

    static final int REDUCE_INVENTORY_FAILED_CODE = 5010;
    static final String REDUCE_INVENTORY_FAILED_MSG = "order not exists";

    static final int IN_LINE_CODE = 5011;
    static final String IN_LINE_MSG = "please wait , in the line!";

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


    //login
    public static CodeMsg USER_NOT_FOUND = new CodeMsg(USER_NOT_FOUND_CODE  , USER_NOT_FOUND_MSG);
    public static CodeMsg USER_SESSION_INVALID = new CodeMsg(USER_SESSION_INVALID_CODE  , USER_SESSION_INVALID_MSG);

    //order
    public static CodeMsg ORDER_NOT_EXISTS = new CodeMsg(ORDER_NOT_EXISTS_CODE  , ORDER_NOT_EXISTS_MSG);
    public static CodeMsg IN_LINE = new CodeMsg(IN_LINE_CODE  , IN_LINE_MSG);

    //reduceInventory
    public static CodeMsg REDUCE_INVENTORY_FAILED = new CodeMsg(REDUCE_INVENTORY_FAILED_CODE  , REDUCE_INVENTORY_FAILED_MSG);

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
