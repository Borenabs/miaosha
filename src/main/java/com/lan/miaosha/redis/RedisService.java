package com.lan.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.lan.miaosha.redis.key.MiaoshaUserKey;
import com.lan.miaosha.redis.key.base.KeyPrefix;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix keyPrefix , String key , Class<T> clazz){
        //如果不释放 ， 会出现 java.util.NoSuchElementException: Timeout waiting for idle object
        Jedis jedis = new Jedis();
        String str = StringUtils.EMPTY;
        try{
             jedis = jedisPool.getResource();
             //生成real Key
            String realKey = keyPrefix.getPrefix() + ":" + key;
            str = jedis.get(realKey);
        }catch (RuntimeException e){
        }finally {
            if(jedis != null) jedis.close();
        }
        return (T)stringToBean(str , clazz);
    }

    public <T> T set(KeyPrefix keyPrefix  , String key ,T value){
        //如果不释放 ， 会出现 java.util.NoSuchElementException: Timeout waiting for idle object
        Jedis jedis = new Jedis();
        //生成realKey
        String realKey = keyPrefix.getPrefix() +":"+ key;
        try{
            jedis = jedisPool.getResource();
        }catch (RuntimeException e){

        }finally {
            if(jedis != null) jedis.close();
        }
        return (T)jedis.set(realKey ,beanToString(value));
    }

    public static <T> String beanToString(T value){
        if (value == null) return null;
        Class<?> clazz  = value.getClass();
        if(clazz == int.class || clazz == Integer.class){
            return ""+value;
        }else if(clazz == String.class){
            return (String) value;
        }else if(clazz == long.class || clazz == Long.class){
            return "" + value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    public static <T> T stringToBean(String str , Class<T> clazz){
        if(StringUtils.isEmpty(str)) return null;

        if(clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class){
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class){
            return (T)Long.getLong(str);
        }else{
            return JSON.toJavaObject( JSON.parseObject(str) , clazz);
    }
    }

    public boolean delete(MiaoshaUserKey keyPrefix, String key) {
        Jedis jedis = new Jedis();
        //生成realKey
        String realKey = keyPrefix.getPrefix() +":"+ key;
        try{
            jedis = jedisPool.getResource();
            if(jedis.exists(realKey)){
                jedis.del(realKey);
                return true;
            }
        }catch (RuntimeException e){

        }finally {
            if(jedis != null) jedis.close();
        }
        return false;
    }

    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() +":"+ key;
            return  jedis.decr(realKey);
        }finally {
            if(jedis != null) jedis.close();
        }
    }

    /**
     * 判断key是否存在
     * */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() +":"+ key;
            return  jedis.exists(realKey);
        }finally {
            if(jedis != null) jedis.close();
        }
    }
}
