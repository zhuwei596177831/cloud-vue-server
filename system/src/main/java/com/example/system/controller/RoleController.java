package com.example.system.controller;

import com.example.api.system.entity.Role;
import com.example.core.entity.Json;
import com.example.core.entity.PageInfo;
import com.example.core.entity.ShiroUser;
import com.example.shiroAuthencation.controller.BaseController;
import com.example.system.req.RoleMenuReq;
import com.example.system.req.RoleReq;
import com.example.system.service.RoleService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author 朱伟伟
 * @date 2021-07-23 14:05:45
 * @description
 */
@Api(tags = "角色")
@ApiSupport(author = "朱伟伟")
@RestController
@RequestMapping("/role")
@Validated
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色分页数据
     *
     * @param roleReq:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:10
     **/
    @ApiOperation(value = "角色分页数据")
    @PostMapping("/rolePageList")
    public Json rolePageList(RoleReq roleReq) {
        PageInfo pageInfo = getPageInfo();
        return Json.ok(roleService.roleList(roleReq, pageInfo));
    }

    /**
     * 添加角色
     *
     * @param role:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:26
     **/
    @ApiOperation(value = "添加角色")
    @PostMapping("/addRole")
    public Json addRole(@RequestBody @Validated({Role.Add.class}) Role role) {
        ShiroUser shiroUser = getUser();
        role.setInputTime(LocalDateTime.now());
        role.setInputUserId(shiroUser.getId());
        return roleService.addRole(role);
    }

    /**
     * 修改角色
     *
     * @param role:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:26
     **/
    @ApiOperation(value = "修改角色")
    @PutMapping("/updateRole")
    public Json updateRole(@RequestBody @Validated({Role.Update.class}) Role role) {
        ShiroUser shiroUser = getUser();
        role.setUpdateTime(LocalDateTime.now());
        role.setUpdateUserId(shiroUser.getId());
        return roleService.updateRole(role);
    }

    /**
     * 删除角色
     *
     * @param id:
     * @author: 朱伟伟
     * @date: 2021-07-23 14:45
     **/
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/deleteRoleById/{id}")
    public Json deleteRoleById(@PathVariable("id") @NotNull(message = "角色id不能为空") Long id) {
        return roleService.deleteRoleById(id);
    }

    /**
     * 角色分配菜单
     *
     * @param roleMenuReq:
     * @author: 朱伟伟
     * @date: 2021-07-23 17:44
     **/
    @ApiOperation(value = "角色分配菜单")
    @PostMapping("/permissionRoleMenus")
    public Json permissionRoleMenus(@RequestBody @Validated RoleMenuReq roleMenuReq) {
        return roleService.permissionRoleMenus(roleMenuReq, getUser().getId());
    }


    /**
     * 所有角色数据
     *
     * @author: 朱伟伟
     * @date: 2021-07-26 10:55
     **/
    @ApiOperation(value = "所有角色数据")
    @PostMapping("/findAllRoles")
    public Json findAllRoles() {
        return Json.ok(roleService.findAllRoles());
    }

    /**
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-05-22 18:48
     * @description: 根据用户id查询角色集合
     **/
    @PostMapping("/findRolesByUserId")
    public Set<Role> findRolesByUserId(@RequestParam Long userId) {
        return roleService.findRolesByUserId(userId);
    }
}
