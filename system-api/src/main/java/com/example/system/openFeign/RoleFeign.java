package com.example.system.openFeign;

import com.example.system.entity.Role;
import com.example.system.openFeign.fallback.RoleFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author 朱伟伟
 * @date 2021-07-25 16:19:53
 * @description role feign
 */
@FeignClient(
        value = "system",
        path = "/system/openFeign/role",
        contextId = "RoleFeign",
        fallbackFactory = RoleFeignFallBack.class
)
public interface RoleFeign {
    /**
     * 根据用户id查询角色集合
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 16:22
     **/
    @PostMapping("/findRolesByUserId")
    Set<Role> findRolesByUserId(@RequestParam("userId") Long userId);
}
