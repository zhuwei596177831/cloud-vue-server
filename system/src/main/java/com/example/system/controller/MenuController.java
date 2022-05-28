package com.example.system.controller;

import com.example.core.entity.Cascader;
import com.example.core.entity.Json;
import com.example.core.enums.MenuType;
import com.example.core.vo.system.UserVo;
import com.example.shiroAuthencation.controller.BaseController;
import com.example.system.entity.Menu;
import com.example.system.entity.req.MenuReq;
import com.example.system.service.MenuService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-06-14 16:52:02
 * @description 菜单http接口
 */
@Api(tags = "菜单")
@ApiSupport(author = "朱伟伟")
@RestController
@RequestMapping("/menu")
@Validated
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * @param menuReq:
     * @author: 朱伟伟
     * @date: 2021-06-14 16:54
     * @description: 菜单数据
     **/
    @ApiOperation(value = "菜单数据")
    @PostMapping("/menuTableList")
    public Json menuTableList(MenuReq menuReq) {
        List<Menu> menuList = menuService.menuTableList(menuReq);
        return Json.ok(menuList);
    }

    /**
     * @param menu:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:12
     * @description: 添加菜单
     **/
    @ApiOperation(value = "添加菜单")
    @PostMapping("/addMenu")
    public Json addMenu(@RequestBody @Validated({Menu.Add.class}) Menu menu) {
        UserVo user = getUser();
        menu.setInputUserId(user.getId());
        menu.setInputTime(LocalDateTime.now());
        Json json = menuService.addMenu(menu);
        menuService.refreshRedisMenuTrees();
        return json;
    }

    /**
     * @param menu:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:12
     * @description: 修改菜单
     **/
    @ApiOperation(value = "修改菜单")
    @PutMapping("/updateMenu")
    public Json updateMenu(@RequestBody @Validated({Menu.Update.class}) Menu menu) {
        UserVo user = getUser();
        menu.setUpdateUserId(user.getId());
        menu.setUpdateTime(LocalDateTime.now());
        Json json = menuService.updateMenu(menu);
        menuService.refreshRedisMenuTrees();
        return json;
    }

    /**
     * @param menuId:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:34
     * @description: 删除菜单
     **/
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(name = "menuId", value = "菜单id", required = true)
    @DeleteMapping("/deleteMenuById/{menuId}")
    public Json deleteMenuById(@PathVariable @NotNull(message = "菜单id不能为空") Long menuId) {
        Json json = menuService.deleteMenuById(menuId);
        menuService.refreshRedisMenuTrees();
        return json;
    }

    /**
     * 用户权限菜单
     *
     * @author: 朱伟伟
     * @date: 2021-06-26 23:25
     **/
    @ApiOperation(value = "用户权限菜单")
    @PostMapping("/userMenus")
    public Json userMenus() {
        UserVo user = getUser();
        return Json.ok(menuService.userMenus(user.getId()));
    }

    /**
     * 菜单列表：菜单树
     *
     * @author: 朱伟伟
     * @date: 2021-06-27 19:51
     **/
    @ApiOperation(value = "菜单列表：菜单树")
    @PostMapping("/menuTrees")
    public Json menuTrees() {
        return Json.ok(menuService.menuTrees());
    }

    /**
     * 菜单类型
     *
     * @author: 朱伟伟
     * @date: 2021-07-22 16:45
     **/
    @ApiOperation(value = "菜单类型")
    @PostMapping("/menuTypes")
    public Json menuTypes() {
        return Json.ok(MenuType.toEnumModel());
    }

    /**
     * 根据菜单类型查询 上级菜单列表下拉数据
     *
     * @param type:
     * @author: 朱伟伟
     * @date: 2021-07-22 17:52
     **/
    @ApiOperation(value = "根据菜单类型查询 上级菜单列表下拉数据")
    @PostMapping("/cascaderTrees")
    public Json cascaderTrees(@RequestParam Integer type) {
        List<Cascader> cascaders = menuService.cascaderTrees(type);
        return Json.ok(cascaders);
    }

    /**
     * 根据菜单类型查询 上级菜单列表下拉数据
     *
     * @param type:
     * @author: 朱伟伟
     * @date: 2021-07-22 17:52
     **/
    @ApiOperation(value = "根据菜单类型查询 上级菜单列表下拉数据")
    @PostMapping("/parentMenus")
    public Json parentMenus(@RequestParam Integer type) {
        if (MenuType.MENU_MODEL.getValue().equals(type)) {
            return Json.ok(Collections.emptyList());
        }
        MenuReq menuReq = new MenuReq();
        if (MenuType.MENU_NAVIGATION.getValue().equals(type)) {
            menuReq.setTypes(Arrays.asList(MenuType.MENU_MODEL.getValue(), MenuType.MENU_NAVIGATION.getValue()));
        } else if (MenuType.MENU_BUTTON.getValue().equals(type)) {
            menuReq.setType(MenuType.MENU_NAVIGATION.getValue());
        } else {
            return Json.ok(Collections.emptyList());
        }
        List<Menu> menuList = menuService.menuList(menuReq, null);
        return Json.ok(menuList);
    }

    /**
     * 权限分配：菜单树
     *
     * @author: 朱伟伟
     * @date: 2021-06-27 19:51
     **/
    @ApiOperation(value = "权限分配：菜单树")
    @PostMapping("/getMenuTrees")
    public Json getMenuTrees() {
        return Json.ok(menuService.getMenuTrees());
    }


}
