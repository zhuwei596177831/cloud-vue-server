package com.example.shiroAuthencation.enums;

import lombok.Getter;

/**
 * @author 朱伟伟
 * @date 2021-06-01 15:27:12
 * @description shiro请求认证 白名单
 * @see com.example.shiroAuthencation.filter.CustomAccessFilter
 */
@Getter
public enum ShiroWhiteListUrl {
    LOGIN("/login", "登录接口"),
    testFilter("/testFilter/**", "测试"),
    USER_BY_LOGIN_NAME("/openFeign/user/findByLoginName", "根据用户名查询用户"),
    ROLES_BY_USER_ID("/openFeign/role/findRolesByUserId", "根据用户id查询角色集合"),
    MENUS_BY_USER_ID("/openFeign/menu/findMenusByUserId", "根据用户id查询菜单集合"),
    SWAGGER_API_DOC("/v2/api-docs/**", "swagger文档"),
    ;

    private final String url;
    private final String desc;

    ShiroWhiteListUrl(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }
}
