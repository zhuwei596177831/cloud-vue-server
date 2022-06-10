package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.api.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-05-15 17:24:16
 * @description 用户mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户列表数据
     *
     * @param user:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:21
     **/
    List<User> userList(User user);
}
