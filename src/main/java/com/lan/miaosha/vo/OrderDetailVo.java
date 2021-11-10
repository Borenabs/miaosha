package com.lan.miaosha.vo;

import com.lan.miaosha.domain.OrderInfo;
import lombok.Data;

@Data
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo orderInfo;

}
