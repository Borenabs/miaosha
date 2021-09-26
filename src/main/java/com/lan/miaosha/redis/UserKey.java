package com.lan.miaosha.redis;

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
