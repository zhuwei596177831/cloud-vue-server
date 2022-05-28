package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 朱伟伟
 * @date 2021-06-14 16:49:35
 * @description 角色菜单关联关系mapper
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
}
