package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2022-06-07 13:45:03
 * @description 登录日志记录Mapper接口
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 查询登录日志记录列表
     *
     * @param loginLog 登录日志记录
     * @return 登录日志记录集合
     */
    List<LoginLog> list(LoginLog loginLog);

}
