package com.lan.miaosha.mapper;

import com.lan.miaosha.domain.MiaoShaUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MiaoshaUserMapper {

    MiaoShaUser selectById(Long id);

}
