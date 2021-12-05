package com.example.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.business.entity.Test;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 朱伟伟
 * @date 2021-12-01 17:31:50
 * @description
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {
}
