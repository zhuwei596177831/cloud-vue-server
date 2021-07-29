package com.example.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.core.entity.PageInfo;
import com.example.core.entity.Result;
import com.example.core.entity.ResultCode;
import com.example.system.entity.*;
import com.example.system.entity.req.RoleMenuReq;
import com.example.system.entity.req.RoleReq;
import com.example.system.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
     * @param roleReq:
     * @param pageInfo:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:10
     **/
    public List<Role> roleList(RoleReq roleReq, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        List<Role> roles = roleMapper.roleList(roleReq);
        if (pageInfo != null) {
            roles.forEach(r -> {
                List<RoleMenu> roleMenus = roleMenuMapper.selectList(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, r.getId()));
                if (!roleMenus.isEmpty()) {
                    List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
                    List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>().in(Menu::getId, menuIds));
                    r.setMenus(menus);
                }
            });
        }
        return roles;
    }

    /**
     * 添加角色
     *
     * @param role:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:27
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result<Integer> addRole(Role role) {
        Result result = validateRole(role);
        if (!result.isSuccess()) {
            return result;
        }
        roleMapper.insert(role);
        return Result.ok();
    }

    /**
     * 修改角色
     *
     * @param role:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:44
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result updateRole(Role role) {
        Result result = validateRole(role);
        if (!result.isSuccess()) {
            return result;
        }
        roleMapper.updateById(role);
        return Result.ok();
    }

    private Result validateRole(Role role) {
        Role exist = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getCode, role.getCode()));
        if (exist != null && !exist.getId().equals(role.getId())) {
            return ResultCode.COMMON.getResult("角色编码已存在");
        }
        exist = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getName, role.getName()));
        if (exist != null && !exist.getId().equals(role.getId())) {
            return ResultCode.COMMON.getResult("角色名称已存在");
        }
        return Result.ok();
    }

    /**
     * 删除角色
     *
     * @param id:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:46
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result deleteRoleById(Long id) {
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getRoleId, id);
        userRoleLambdaQueryWrapper.last("limit 1");
        UserRole userRole = userRoleMapper.selectOne(userRoleLambdaQueryWrapper);
        if (userRole != null) {
            return ResultCode.COMMON.getResult("角色已和用户绑定!");
        }
        roleMapper.deleteById(id);
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuLambdaQueryWrapper.eq(RoleMenu::getRoleId, id);
        roleMenuMapper.delete(roleMenuLambdaQueryWrapper);
        return Result.ok();
    }

    /**
     * 角色分配菜单
     *
     * @param roleMenuReq:
     * @author: 朱伟伟
     * @date: 2021-07-23 17:47
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result permissionRoleMenus(RoleMenuReq roleMenuReq, Long inputUserId) {
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleMenuReq.getRoleId()));
        roleMenuReq.getMenus().forEach(r -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setInputUserId(inputUserId);
            roleMenu.setInputTime(LocalDateTime.now());
            roleMenu.setRoleId(roleMenuReq.getRoleId());
            roleMenu.setMenuId(r);
            roleMenuMapper.insert(roleMenu);
        });
        Role role = new Role();
        role.setId(roleMenuReq.getRoleId());
        role.setCheckedMenuIds(roleMenuReq.getCheckedMenuIds());
        roleMapper.updateById(role);
        return Result.ok();
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
