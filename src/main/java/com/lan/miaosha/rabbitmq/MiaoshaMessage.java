package com.lan.miaosha.rabbitmq;

import com.lan.miaosha.domain.MiaoshaUser;
import lombok.Data;

@Data
public class MiaoshaMessage {
    private MiaoshaUser miaoshaUser;
    private long goodsId;
}
