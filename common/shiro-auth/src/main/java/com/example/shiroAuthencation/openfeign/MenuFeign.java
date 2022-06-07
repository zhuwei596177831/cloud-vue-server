package com.example.shiroAuthencation.openfeign;

import com.example.core.util.Constants;
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
        value = Constants.APPLICATION_NAME_SYSTEM,
        path = Constants.CONTEXT_PATH_SYSTEM + Constants.OPENFEIGN_CALL_PREFIX + "/menu",
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
