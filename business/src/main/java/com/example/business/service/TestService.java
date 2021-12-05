package com.example.business.service;

import com.example.business.entity.Test;
import com.example.business.mapper.TestMapper;
import com.example.core.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 朱伟伟
 * @date 2021-12-01 17:32:04
 * @description
 */
@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    @Transactional(rollbackFor = Exception.class)
    public Result saveTest() {
        Test test = new Test();
        test.setName("test");
        testMapper.insert(test);
        return Result.ok();
    }

}
