package com.lan.miaosha.redis.key;

import com.lan.miaosha.redis.key.base.BasePrefix;

public class UserKey extends BasePrefix {

    public UserKey(String prefix) {
        super( prefix);
    }

    public UserKey(int redisSetTest, String prefix) {
        super(redisSetTest, prefix);
    }

    public static UserKey getByiId = new UserKey("id");
    public static UserKey getByName = new UserKey("name");

}
