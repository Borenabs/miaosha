package com.lan.miaosha.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.max-active}")
    private int poolMaxTotal;
    @Value("${spring.redis.pool.max-idle}")
    private int poolMaxIdle;
    @Value("${spring.redis.pool.max-wait}")
    private int poolMaxWait;
}
