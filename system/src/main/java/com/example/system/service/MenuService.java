package com.example.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.api.system.entity.Menu;
import com.example.api.system.entity.RoleMenu;
import com.example.core.entity.Cascader;
import com.example.core.entity.Json;
import com.example.core.entity.MenuTree;
import com.example.core.entity.PageInfo;
import com.example.core.enums.MenuType;
import com.example.core.enums.RedisKey;
import com.example.coreweb.cache.util.RedisUtil;
import com.example.coreweb.exception.ApplicationException;
import com.example.coreweb.rescode.system.MenuResponseCode;
import com.example.system.mapper.MenuMapper;
import com.example.system.mapper.RoleMenuMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RedisUtil redisUtil;

    public List<Menu> menuTableList(Menu menu) {
        return reconstructMenuList(this.menuList(menu, null), menu.getType());
    }

    private List<Menu> reconstructMenuList(List<Menu> all, Integer type) {
        if (type == null) {
            List<Menu> moduleMenus = all.stream().filter(m -> MenuType.MENU_MODEL.getValue().equals(m.getType())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(moduleMenus)) {
                recursionChildren(moduleMenus, all);
            }
            return moduleMenus;
        } else {
            return all;
        }
    }

    /**
     * @param menu:
     * @param pageInfo:
     * @author: 朱伟伟
     * @date: 2021-06-14 16:57
     * @description: 菜单列表数据
     **/
    public List<Menu> menuList(Menu menu, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return menuMapper.menuList(menu);
    }

    /**
     * @param menu:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:11
     * @description: 添加菜单
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json addMenu(Menu menu) {
        validateMenu(menu);
        menuMapper.insert(menu);
        return Json.success();
    }

    /**
     * @param menu:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:23
     * @description: 修改菜单
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json updateMenu(Menu menu) {
        validateMenu(menu);
        menuMapper.updateById(menu);
        return Json.success();
    }

    private void validateMenu(Menu menu) {
        if (!MenuType.MENU_MODEL.getValue().equals(menu.getType())) {
            if (menu.getParentId() == null) {
                throw new ApplicationException(MenuResponseCode.PARENT_ID_IS_NULL);
            }
        }
        //if (MenuType.MENU_MODEL.getValue().equals(menu.getType())) {
        //    if (!StringUtils.hasText(menu.getIconClass())) {
        //        throw new ApplicationException(MenuResponseCode.ICON_IS_NULL);
        //    }
        //}
        if (MenuType.MENU_NAVIGATION.getValue().equals(menu.getType())) {
            if (!StringUtils.hasText(menu.getPath())) {
                throw new ApplicationException(MenuResponseCode.PATH_IS_NULL);
            }
        }
        List<Menu> menus = this.menuList(new Menu(), null);
        boolean existFlag = menus.stream().anyMatch(m -> m.getCode().equals(menu.getCode()) && !m.getId().equals(menu.getId()));
        if (existFlag) {
            throw new ApplicationException(MenuResponseCode.CODE_EXIST);
        }
        existFlag = menus.stream().anyMatch(m -> m.getName().equals(menu.getName()) && !m.getId().equals(menu.getId()));
        if (existFlag) {
            throw new ApplicationException(MenuResponseCode.NAME_EXIST);
        }
        if (MenuType.MENU_BUTTON.getValue().equals(menu.getType())) {
            menu.setPath(null);
            menu.setIconClass(null);
        }
        if (MenuType.MENU_MODEL.getValue().equals(menu.getType())) {
            menu.setParentId(null);
        }
    }

    /**
     * @param menuId:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:35
     * @description: 删除菜单
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json deleteMenuById(Long menuId) {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getParentId, menuId);
        List<Menu> menus = menuMapper.selectList(menuLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(menus)) {
            throw new ApplicationException(MenuResponseCode.DELETE_CHILD_MENU_FIRST);
        }
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuLambdaQueryWrapper.eq(RoleMenu::getMenuId, menuId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(roleMenus)) {
            throw new ApplicationException(MenuResponseCode.ALREADY_BIND_ROLE);
        }
        menuMapper.deleteById(menuId);
        return Json.success();
    }

    private void recursionChildren(Collection<Menu> moduleMenus, Collection<Menu> all) {
        moduleMenus.forEach(m -> {
            Set<Menu> childrenSet = all.stream().filter(a -> m.getId().equals(a.getParentId())).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(childrenSet)) {
                recursionChildren(childrenSet, all);
            }
            m.setChildren(new ArrayList<>(childrenSet));
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
            menuTrees = redisUtil.getList(RedisKey.MENU_TREES.getKey(), MenuTree.class);
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
        redisUtil.set(RedisKey.MENU_TREES.getKey(), menuTrees);
        return menuTrees;
    }

    private List<MenuTree> getMenuTrees() {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getType, MenuType.MENU_MODEL.getValue());
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
        Menu query = new Menu();
        if (MenuType.MENU_MODEL.getValue().equals(type)) {
            return Collections.emptyList();
        } else if (MenuType.MENU_NAVIGATION.getValue().equals(type)) {
            query.setTypes(Arrays.asList(MenuType.MENU_MODEL.getValue(), MenuType.MENU_NAVIGATION.getValue()));
        } else {
            query.setType(MenuType.MENU_NAVIGATION.getValue());
        }
        List<Menu> allMenus = this.menuList(query, null);
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

