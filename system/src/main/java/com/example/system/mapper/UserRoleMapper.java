package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.api.system.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 朱伟伟
 * @date 2021-06-14 16:47:49
 * @description 用户角色关联关系mapper
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
