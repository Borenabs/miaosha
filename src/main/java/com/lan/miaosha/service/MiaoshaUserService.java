package com.lan.miaosha.service;

import com.lan.miaosha.domain.MiaoShaUser;
import com.lan.miaosha.mapper.MiaoshaUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaUserService {

    @Autowired
    MiaoshaUserMapper miaoshaUserMapper;

    public MiaoShaUser selectById(Long userId){
        return miaoshaUserMapper.selectById(userId);
    }
}
