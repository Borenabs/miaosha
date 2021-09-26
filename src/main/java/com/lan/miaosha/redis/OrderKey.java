package com.lan.miaosha.redis;

public class OrderKey extends BasePrefix {
    public OrderKey(int redisSetTest, String prefix) {
        super(redisSetTest, prefix);
    }
}
