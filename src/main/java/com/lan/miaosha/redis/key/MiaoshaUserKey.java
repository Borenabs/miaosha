package com.lan.miaosha.redis.key;

import com.lan.miaosha.redis.key.base.BasePrefix;

public class MiaoshaUserKey extends BasePrefix {

    private static final int TOKEN_EXPIRE = 24*3600*2;

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"token");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0 , "miaoshaUser");

    public MiaoshaUserKey(int redisSetTest, String prefix) {
        super(redisSetTest, prefix);
    }
}
