package com.lan.miaosha.redis;

public interface KeyPrefix{

    public int expireSecond();

    public String getPrefix();
}
