package com.example.system.service;

import com.example.system.mapper.RoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 朱伟伟
 * @date 2021-06-14 16:50:09
 * @description 角色菜单关联关系service
 */
@Service
public class RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

}
