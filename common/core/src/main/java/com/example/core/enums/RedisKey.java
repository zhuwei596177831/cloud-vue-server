package com.example.core.enums;

import lombok.Getter;

/**
 * @author 朱伟伟
 * @date 2021-06-01 15:27:12
 * @description redis key enum
 */
@Getter
public enum RedisKey {
    SHIRO_SESSION_CACHE("shiro_session", "shiro session key"),
    MENU_TREES("menu_trees", "菜单tree"),
    ;
    private final String key;
    private final String desc;

    RedisKey(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
