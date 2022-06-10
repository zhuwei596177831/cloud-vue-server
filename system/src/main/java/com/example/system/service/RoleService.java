package com.example.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.api.system.entity.Role;
import com.example.api.system.entity.RoleMenu;
import com.example.api.system.entity.UserRole;
import com.example.core.entity.Json;
import com.example.core.entity.PageInfo;
import com.example.coreweb.exception.ApplicationException;
import com.example.coreweb.rescode.system.RoleResponseCode;
import com.example.system.mapper.MenuMapper;
import com.example.system.mapper.RoleMapper;
import com.example.system.mapper.RoleMenuMapper;
import com.example.system.mapper.UserRoleMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author 朱伟伟
 * @date 2021-06-14 16:47:17
 * @description 角色sercie
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 角色列表数据
     *
     * @param role:
     * @param pageInfo:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:10
     **/
    public List<Role> roleList(Role role, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return roleMapper.roleList(role);
    }

    /**
     * 添加角色
     *
     * @param role:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:27
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json addRole(Role role) {
        validateRole(role);
        roleMapper.insert(role);
        return Json.success();
    }

    /**
     * 修改角色
     *
     * @param role:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:44
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json updateRole(Role role) {
        validateRole(role);
        roleMapper.updateById(role);
        return Json.success();
    }

    private void validateRole(Role role) {
        Role exist = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getCode, role.getCode()));
        if (exist != null && !exist.getId().equals(role.getId())) {
            throw new ApplicationException(RoleResponseCode.CODE_EXIST);
        }
        exist = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getName, role.getName()));
        if (exist != null && !exist.getId().equals(role.getId())) {
            throw new ApplicationException(RoleResponseCode.NAME_EXIST);
        }
    }

    /**
     * 删除角色
     *
     * @param id:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:46
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json deleteRoleById(Long id) {
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getRoleId, id);
        userRoleLambdaQueryWrapper.last("limit 1");
        UserRole userRole = userRoleMapper.selectOne(userRoleLambdaQueryWrapper);
        if (userRole != null) {
            throw new ApplicationException(RoleResponseCode.ALREADY_BIND_USER);
        }
        roleMapper.deleteById(id);
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuLambdaQueryWrapper.eq(RoleMenu::getRoleId, id);
        roleMenuMapper.delete(roleMenuLambdaQueryWrapper);
        return Json.success();
    }

    /**
     * 角色分配菜单
     *
     * @param query:
     * @author: 朱伟伟
     * @date: 2021-07-23 17:47
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json permissionRoleMenus(RoleMenu query, Long inputUserId) {
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, query.getRoleId()));
        query.getMenus().forEach(r -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setInputUserId(inputUserId);
            roleMenu.setInputTime(LocalDateTime.now());
            roleMenu.setRoleId(query.getRoleId());
            roleMenu.setMenuId(r);
            roleMenuMapper.insert(roleMenu);
        });
        Role role = new Role();
        role.setId(query.getRoleId());
        role.setCheckedMenuIds(query.getCheckedMenuIds());
        roleMapper.updateById(role);
        return Json.success();
    }

    /**
     * 根据用户id查询角色集合
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 16:27
     **/
    public Set<Role> findRolesByUserId(Long userId) {
        return roleMapper.findRolesByUserId(userId);
    }

    /**
     * 所有角色数据
     *
     * @author: 朱伟伟
     * @date: 2021-07-26 10:56
     **/
    public List<Role> findAllRoles() {
        return roleMapper.selectList(new QueryWrapper<>());
    }
}
