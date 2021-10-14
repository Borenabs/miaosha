package com.lan.miaosha.redis.key.base;

public interface KeyPrefix{

    public int expireSecond();

    public String getPrefix();
}
