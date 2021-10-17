package com.lan.miaosha.mapper;

import com.lan.miaosha.domain.MiaoShaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MiaoshaUserMapper {

    @Select("select * from miaosha_user where id = #{id}")
    public MiaoShaUser selectById(@Param("id") Long  userId);

}
