package com.lan.miaosha.mapper;

import com.lan.miaosha.domain.Test;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TestMapper {
    List<Test> testList();

    Integer insert(Test test);
}
