package com.lan.miaosha.mapper;

import com.lan.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MiaoshaUserMapper {

    MiaoshaUser selectById(Long id);

}
