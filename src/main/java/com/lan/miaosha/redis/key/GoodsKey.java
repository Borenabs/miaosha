package com.lan.miaosha.redis.key;

import com.lan.miaosha.redis.key.base.BasePrefix;

public class GoodsKey extends BasePrefix {

    private GoodsKey(int redisSetTest, String prefix) {
        super(redisSetTest, prefix);
    }

    public GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey goodsList = new GoodsKey(60 , "goodsList");
    public static GoodsKey goodsDetail = new GoodsKey(60 , "goodsDetail");
    public static GoodsKey goodsInventory = new GoodsKey(60 , "goodsInventory");
    public static GoodsKey goodsMiaoshaOver = new GoodsKey("miaoshaOver");
}
