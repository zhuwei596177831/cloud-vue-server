package com.example.system.controller;

import com.example.core.entity.Json;
import org.apache.shiro.SecurityUtils;
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
    public Json hasRoles(@RequestBody List<String> codes) {
        return Json.ok(SecurityUtils.getSubject().hasAllRoles(codes));
    }

    /**
     * 权限控制——按钮——菜单
     *
     * @param code: 菜单权限编码
     * @author: 朱伟伟
     * @date: 2021-07-25 17:55
     **/
    @PostMapping("/hasPermission")
    public Json hasPermission(@RequestParam String code) {
        return Json.ok(SecurityUtils.getSubject().isPermitted(code));
    }

}
