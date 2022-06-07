package com.example.shiroAuthencation.openfeign;

import com.example.core.util.Constants;
import com.example.core.vo.system.RoleVo;
import com.example.shiroAuthencation.openfeign.fallback.RoleFeignFallBack;
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
        path = Constants.CONTEXT_PATH_SYSTEM + Constants.OPENFEIGN_CALL_PREFIX + "/role",
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
    Set<RoleVo> findRolesByUserId(@RequestParam("userId") Long userId);
}
