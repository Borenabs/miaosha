package com.lan.miaosha.redis.key;

import com.lan.miaosha.redis.key.base.BasePrefix;

public class OrderKey extends BasePrefix {
    public static OrderKey miaoshaOrderKey = new OrderKey("miaoshaOrder");

    public OrderKey(int redisSetTest, String prefix) {
        super(redisSetTest, prefix);
    }

    public OrderKey(String prefix) {
        super(prefix);
    }
}
