package com.example.system.controller;

import com.example.core.entity.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-07-25 17:52:39
 * @description 权限控制
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    /**
     * 权限控制——按钮——角色
     *
     * @param codes: 角色编码 ,分割
     * @author: 朱伟伟
     * @date: 2021-07-27 14:19
     **/
    @PostMapping("/hasRoles")
    public Result<Boolean> hasRoles(@RequestBody List<String> codes) {
        return Result.ok(SecurityUtils.getSubject().hasAllRoles(codes));
    }

    /**
     * 权限控制——按钮——菜单
     *
     * @param code: 菜单权限编码
     * @author: 朱伟伟
     * @date: 2021-07-25 17:55
     **/
    @PostMapping("/hasPermission")
    public Result<Boolean> hasPermission(@RequestParam String code) {
        return Result.ok(SecurityUtils.getSubject().isPermitted(code));
    }

}
