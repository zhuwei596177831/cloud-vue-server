package com.example.system.controller.feign;

import com.example.core.vo.system.MenuVo;
import com.example.system.entity.Menu;
import com.example.system.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<MenuVo> findMenusByUserId(@RequestParam Long userId) {
        Set<Menu> menus = menuService.findMenusByUserId(userId);
        return menus.stream().map(menu -> {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(menu, menuVo);
            return menuVo;
        }).collect(Collectors.toSet());
    }

}
