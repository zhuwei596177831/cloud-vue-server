package com.example.api.system.openfeign.fallback;

import com.example.api.system.entity.User;
import com.example.api.system.openfeign.client.UserFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2021-07-25 16:13:08
 * @description User Feign fallback
 */
@Component
@Slf4j
public class UserFeignFallBack implements FallbackFactory<UserFeign> {
    @Override
    public UserFeign create(Throwable cause) {
        cause.printStackTrace();
        return new UserFeign() {
            @Override
            public User findByLoginName(String loginName) {
                log.error("User Feign 根据用户名:{}查询用户异常:{}", loginName, cause.getMessage());
                return null;
            }
        };
    }
}
