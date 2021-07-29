package com.example.system.openFeign.fallback;

import com.example.system.entity.Menu;
import com.example.system.openFeign.MenuFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * @author 朱伟伟
 * @date 2021-07-25 16:39:41
 * @description menu feign fallback
 */
@Component
@Slf4j
public class MenuFeignFallBack implements FallbackFactory<MenuFeign> {
    @Override
    public MenuFeign create(Throwable cause) {
        return new MenuFeign() {
            @Override
            public Set<Menu> findMenusByUserId(Long userId) {
                cause.printStackTrace();
                log.error("Menu Feign 根据用户id:{}查询菜单集合异常:{}", userId, cause.getMessage());
                return Collections.emptySet();
            }
        };
    }
}
