package com.lan.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix{

    private int redisSetTest;
    private String prefix;

    public BasePrefix( int redisSetTest , String prefix){
        this.redisSetTest = redisSetTest;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {//默认0代表永不过期
        this(0, prefix);
    }

    @Override
    public int expireSecond() {
        return redisSetTest;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }
}
