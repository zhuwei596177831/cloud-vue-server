package com.example.system.controller.feign;

import com.example.system.entity.Menu;
import com.example.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Role Feign controller
 *
 * @author: 朱伟伟
 * @date: 2021-07-25 16:24
 **/
@RestController
@RequestMapping("/openFeign/menu")
public class MenuFeignController {

    @Autowired
    private MenuService menuService;

    /**
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-05-22 18:48
     * @description: 根据用户id查询菜单集合
     **/
    @PostMapping("/findMenusByUserId")
    public Set<Menu> findMenusByUserId(@RequestParam Long userId) {
        return menuService.findMenusByUserId(userId);
    }

}
