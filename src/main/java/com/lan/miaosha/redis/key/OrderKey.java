package com.lan.miaosha.redis.key;

import com.lan.miaosha.redis.key.base.BasePrefix;

public class OrderKey extends BasePrefix {
    public OrderKey(int redisSetTest, String prefix) {
        super(redisSetTest, prefix);
    }
}
