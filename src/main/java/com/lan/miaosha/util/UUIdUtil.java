package com.lan.miaosha.util;

import java.util.UUID;

public class UUIdUtil {
    public static String uuid(){
        //原生uuid是带 ‘-’ ， 这里去掉。
        return UUID.randomUUID().toString().replace("-" , "");
    }
}
