package com.example.system.openfeign.server;

import com.example.core.vo.system.RoleVo;
import com.example.system.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Role Feign controller
 *
 * @author: 朱伟伟
 * @date: 2021-07-25 16:24
 **/
@RestController
@RequestMapping("/openFeign/role")
public class RoleFeignController {

    @Autowired
    private RoleService roleService;

    /**
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-05-22 18:48
     * @description: 根据用户id查询角色集合
     **/
    @PostMapping("/findRolesByUserId")
    public Set<RoleVo> findRolesByUserId(@RequestParam Long userId) {
        return roleService.findRolesByUserId(userId).stream()
                .map(role -> {
                    RoleVo roleVo = new RoleVo();
                    BeanUtils.copyProperties(role, roleVo);
                    return roleVo;
                }).collect(Collectors.toSet());
    }

}
