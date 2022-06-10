package com.example.api.system.openfeign.fallback;

import com.example.api.system.entity.Role;
import com.example.api.system.openfeign.client.RoleFeign;
import com.example.core.entity.GenericJson;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        cause.printStackTrace();
        return new RoleFeign() {
            @Override
            public GenericJson<Set<Role>> findRolesByUserId(Long userId) {
                log.error("Role Feign 根据用户id:{}查询角色集合异常:{}", userId, cause.getMessage());
                return GenericJson.fail(cause.getMessage());
            }
        };
    }

}
