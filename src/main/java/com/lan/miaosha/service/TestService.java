package com.lan.miaosha.service;

import com.lan.miaosha.domain.Test;
import com.lan.miaosha.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    TestMapper testMapper;

   public List<Test> testList(){
        return testMapper.testList();
    }

    public int insertTest(Test test){
       return testMapper.insert(test);
    }
}
