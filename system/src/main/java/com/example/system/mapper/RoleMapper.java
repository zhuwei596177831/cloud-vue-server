package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.api.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author 朱伟伟
 * @date 2021-06-14 16:46:50
 * @description 角色mapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 角色列表数据
     *
     * @param role:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:16
     **/
    List<Role> roleList(Role role);

    /**
     * 根据用户id查询角色集合
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 16:33
     **/
    Set<Role> findRolesByUserId(Long userId);
}
