package com.example.system.service;

import com.example.system.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 朱伟伟
 * @date 2021-06-14 16:48:16
 * @description 用户角色关联关系service
 */
@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

}
