package com.example.shiroAuthencation.openfeign;

import com.example.core.vo.system.MenuVo;
import com.example.shiroAuthencation.openfeign.fallback.MenuFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author 朱伟伟
 * @date 2021-07-25 16:38:46
 * @description menu feign
 */
@FeignClient(
        value = "system",
        path = "/system/openFeign/menu",
        contextId = "MenuFeign",
        fallbackFactory = MenuFeignFallBack.class
)
public interface MenuFeign {

    /**
     * 根据用户id查询菜单集合
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 16:41
     **/
    @PostMapping("/findMenusByUserId")
    Set<MenuVo> findMenusByUserId(@RequestParam("userId") Long userId);

}
