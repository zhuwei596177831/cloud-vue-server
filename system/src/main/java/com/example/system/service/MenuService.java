package com.example.system.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.core.entity.*;
import com.example.core.enums.MenuType;
import com.example.core.enums.RedisKey;
import com.example.system.entity.Menu;
import com.example.system.entity.RoleMenu;
import com.example.system.entity.req.MenuReq;
import com.example.system.mapper.MenuMapper;
import com.example.system.mapper.RoleMenuMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 朱伟伟
 * @date 2021-06-14 16:46:22
 * @description 菜单service
 */
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @param menuReq:
     * @param pageInfo:
     * @author: 朱伟伟
     * @date: 2021-06-14 16:57
     * @description: 菜单列表数据
     **/
    public List<Menu> menuList(MenuReq menuReq, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return menuMapper.menuList(menuReq);
    }

    /**
     * @param menu:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:11
     * @description: 添加菜单
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result addMenu(Menu menu) {
        Result result = validateMenu(menu);
        if (!result.isSuccess()) {
            return result;
        }
        menuMapper.insert(menu);
        return Result.ok();
    }

    /**
     * @param menu:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:23
     * @description: 修改菜单
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result updateMenu(Menu menu) {
        Result result = validateMenu(menu);
        if (!result.isSuccess()) {
            return result;
        }
        menuMapper.updateById(menu);
        return Result.ok();
    }

    private Result validateMenu(Menu menu) {
        if (!MenuType.MENU_MODEL.getValue().equals(menu.getType())) {
            if (menu.getParentId() == null) {
                return ResultCode.COMMON.getResult("上级菜单不能为空");
            }
        }
        if (MenuType.MENU_MODEL.getValue().equals(menu.getType())) {
            if (!StringUtils.hasText(menu.getIconClass())) {
                return ResultCode.COMMON.getResult("菜单图标不能为空");
            }
        }
        if (MenuType.MENU_NAVIGATION.getValue().equals(menu.getType())) {
            if (!StringUtils.hasText(menu.getPath())) {
                return ResultCode.COMMON.getResult("菜单路径不能为空");
            }
        }
        List<Menu> menus = this.menuList(new MenuReq(), null);
        boolean existFlag = menus.stream().anyMatch(m -> m.getCode().equals(menu.getCode()) && !m.getId().equals(menu.getId()));
        if (existFlag) {
            return ResultCode.COMMON.getResult("菜单编码已存在");
        }
        existFlag = menus.stream().anyMatch(m -> m.getName().equals(menu.getName()) && !m.getId().equals(menu.getId()));
        if (existFlag) {
            return ResultCode.COMMON.getResult("菜单名称已存在");
        }
        if (MenuType.MENU_BUTTON.getValue().equals(menu.getType())) {
            menu.setPath(null);
            menu.setIconClass(null);
        }
        if (MenuType.MENU_MODEL.getValue().equals(menu.getType())) {
            menu.setParentId(null);
        }
        return Result.ok();
    }

    /**
     * @param menuId:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:35
     * @description: 删除菜单
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result deleteMenuById(Long menuId) {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getParentId, menuId);
        List<Menu> menus = menuMapper.selectList(menuLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(menus)) {
            return ResultCode.COMMON.getResult("请先删除菜单下的子菜单!");
        }
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuLambdaQueryWrapper.eq(RoleMenu::getMenuId, menuId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(roleMenus)) {
            return ResultCode.COMMON.getResult("菜单已和角色绑定!");
        }
        menuMapper.deleteById(menuId);
        return Result.ok();
    }

    /**
     * 查询用户所有的权限菜单
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-06-26 22:34
     **/
    public List<Menu> userMenus(Long userId) {
        Set<Menu> all = this.findMenusByUserId(userId).stream().filter(m -> !MenuType.MENU_BUTTON.getValue().equals(m.getType())).collect(Collectors.toSet());
        Set<Menu> moduleMenus = all.stream().filter(m -> MenuType.MENU_MODEL.getValue().equals(m.getType())).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(moduleMenus)) {
            recursionChildren(moduleMenus, all);
        }
        return new ArrayList<>(moduleMenus);
    }

    private void recursionChildren(Collection<Menu> moduleMenus, Collection<Menu> all) {
        moduleMenus.forEach(m -> {
            Set<Menu> childrenSet = all.stream().filter(a -> m.getId().equals(a.getParentId())).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(childrenSet)) {
                recursionChildren(childrenSet, all);
            }
            m.setChildMenus(new ArrayList<>(childrenSet));
        });
    }

    /**
     * 菜单树
     *
     * @author: 朱伟伟
     * @date: 2021-06-27 19:52
     **/
    public List<MenuTree> menuTrees() {
        List<MenuTree> menuTrees = null;
        try {
            menuTrees = JSON.parseArray(stringRedisTemplate.opsForValue().get(RedisKey.MENU_TREES.getKey()), MenuTree.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CollectionUtils.isEmpty(menuTrees)) {
            menuTrees = refreshRedisMenuTrees();
        }
        return menuTrees;
    }

    public List<MenuTree> refreshRedisMenuTrees() {
        List<MenuTree> menuTrees = getMenuTrees();
        MenuTree all = new MenuTree();
        all.setId(0L);
        all.setLabel("全部");
        all.setType(MenuType.MENU_MODEL.getValue());
        all.setChildren(menuTrees);
        stringRedisTemplate.opsForValue().set(RedisKey.MENU_TREES.getKey(), JSON.toJSONString(Collections.singletonList(all)));
        return menuTrees;
    }

    public List<MenuTree> getMenuTrees() {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getType, MenuType.MENU_MODEL.getValue());
        menuLambdaQueryWrapper.orderByAsc(Menu::getSort);
        List<MenuTree> menuTrees = menuMapper.selectList(menuLambdaQueryWrapper).stream().map(menu -> {
            MenuTree menuTree = new MenuTree();
            menuTree.setId(menu.getId());
            menuTree.setLabel(menu.getName());
            menuTree.setType(menu.getType());
            return menuTree;
        }).collect(Collectors.toList());
        recursionTreeMenus(menuTrees);
        return menuTrees;
    }

    private void recursionTreeMenus(List<MenuTree> menuTrees) {
        menuTrees.forEach(treeItem -> {
            LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
            menuLambdaQueryWrapper.eq(Menu::getParentId, treeItem.getId());
            menuLambdaQueryWrapper.orderByAsc(Menu::getSort);
            List<MenuTree> menuTreeList = menuMapper.selectList(menuLambdaQueryWrapper).stream().map(menu -> {
                MenuTree menuTree = new MenuTree();
                menuTree.setId(menu.getId());
                menuTree.setLabel(menu.getName());
                menuTree.setType(menu.getType());
                return menuTree;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(menuTreeList)) {
                recursionTreeMenus(menuTreeList);
            }
            treeItem.setChildren(menuTreeList);
        });
    }

    /**
     * 根据用户id查询菜单集合
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 16:44
     **/
    public Set<Menu> findMenusByUserId(Long userId) {
        return menuMapper.findMenusByUserId(userId);
    }

    public List<Cascader> cascaderTrees(Integer type) {
        MenuReq menuReq = new MenuReq();
        if (MenuType.MENU_MODEL.getValue().equals(type)) {
            return Collections.emptyList();
        } else if (MenuType.MENU_NAVIGATION.getValue().equals(type)) {
            menuReq.setTypes(Arrays.asList(MenuType.MENU_MODEL.getValue(), MenuType.MENU_NAVIGATION.getValue()));
        } else {
            menuReq.setType(MenuType.MENU_NAVIGATION.getValue());
        }
        List<Menu> allMenus = this.menuList(menuReq, null);
        List<Cascader> allCascaders = allMenus.stream().map(m -> {
            Cascader cascader = new Cascader();
            cascader.setLabel(m.getName());
            cascader.setValue(String.valueOf(m.getId()));
            cascader.setParentValue(String.valueOf(m.getParentId()));
            return cascader;
        }).collect(Collectors.toList());
        if (MenuType.MENU_BUTTON.getValue().equals(type)) {
            return allCascaders;
        }
        List<Menu> menus = allMenus;
        if (MenuType.MENU_NAVIGATION.getValue().equals(type)) {
            menus = allMenus.stream().filter(m -> MenuType.MENU_MODEL.getValue().equals(m.getType())).collect(Collectors.toList());
        }
        List<Cascader> cascaders = menus.stream().map(m -> {
            Cascader cascader = new Cascader();
            cascader.setLabel(m.getName());
            cascader.setValue(String.valueOf(m.getId()));
            cascader.setParentValue(String.valueOf(m.getParentId()));
            return cascader;
        }).collect(Collectors.toList());
        recursionCascader(cascaders, allCascaders);
        return cascaders;
    }

    private void recursionCascader(List<Cascader> cascaders, List<Cascader> all) {
        cascaders.forEach(c -> {
            List<Cascader> children = all.stream().filter(a -> c.getValue().equals(a.getParentValue())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(children)) {
                recursionCascader(children, all);
            }
            c.setChildren(children);
        });
    }

}

