package com.example.api.system.openfeign.client;

import com.example.api.system.entity.Menu;
import com.example.api.system.openfeign.fallback.MenuFeignFallBack;
import com.example.core.entity.GenericJson;
import com.example.core.util.Constants;
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
        value = Constants.APPLICATION_NAME_SYSTEM,
        path = Constants.CONTEXT_PATH_SYSTEM + "/menu",
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
    GenericJson<Set<Menu>> findMenusByUserId(@RequestParam("userId") Long userId);

}
