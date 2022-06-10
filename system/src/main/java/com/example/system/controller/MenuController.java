package com.example.system.controller;

import com.example.api.system.entity.Menu;
import com.example.core.entity.Cascader;
import com.example.core.entity.Json;
import com.example.core.entity.ShiroUser;
import com.example.core.enums.MenuType;
import com.example.core.util.Constants;
import com.example.coreweb.annotation.Log;
import com.example.coreweb.enums.BusinessType;
import com.example.shiroAuthencation.controller.BaseController;
import com.example.system.req.MenuReq;
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
import java.util.List;
import java.util.Set;

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
    @Log(title = Constants.APPLICATION_NAME_SYSTEM_LOG, businessType = BusinessType.INSERT)
    @ApiOperation(value = "添加菜单")
    @PostMapping("/addMenu")
    public Json addMenu(@RequestBody @Validated({Menu.Add.class}) Menu menu) {
        ShiroUser shiroUser = getUser();
        menu.setInputUserId(shiroUser.getId());
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
        ShiroUser shiroUser = getUser();
        menu.setUpdateUserId(shiroUser.getId());
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
     * 权限分配：菜单树
     *
     * @author: 朱伟伟
     * @date: 2021-06-27 19:51
     **/
    @ApiOperation(value = "权限分配：菜单树")
    @PostMapping("/menuTrees")
    public Json getMenuTrees() {
        return Json.ok(menuService.menuTrees());
    }

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
