package com.example.shiroAuthencation.openfeign.fallback;

import com.example.core.vo.system.RoleVo;
import com.example.shiroAuthencation.openfeign.RoleFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * @author 朱伟伟
 * @date 2021-07-25 16:13:08
 * @description Role Feign fallback
 */
@Component
@Slf4j
public class RoleFeignFallBack implements FallbackFactory<RoleFeign> {
    @Override
    public RoleFeign create(Throwable cause) {
        return new RoleFeign() {
            @Override
            public Set<RoleVo> findRolesByUserId(Long userId) {
                cause.printStackTrace();
                log.error("Role Feign 根据用户id:{}查询角色集合异常:{}", userId, cause.getMessage());
                return Collections.emptySet();
            }
        };
    }

}
