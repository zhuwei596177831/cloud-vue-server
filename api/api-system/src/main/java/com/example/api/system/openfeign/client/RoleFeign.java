package com.example.api.system.openfeign.client;

import com.example.api.system.entity.Role;
import com.example.api.system.openfeign.fallback.RoleFeignFallBack;
import com.example.core.util.Constants;
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
        value = Constants.APPLICATION_NAME_SYSTEM,
        path = Constants.CONTEXT_PATH_SYSTEM + "/role",
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
