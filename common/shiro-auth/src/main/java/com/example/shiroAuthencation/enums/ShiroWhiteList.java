package com.example.shiroAuthencation.enums;

import lombok.Getter;

/**
 * @author 朱伟伟
 * @date 2021-06-01 15:27:12
 * @description shiro请求认证 白名单
 * @see com.example.shiroAuthencation.filter.CustomAccessFilter
 */
@Getter
public enum ShiroWhiteList {
    LOGIN("/login", "登录接口"),
    ACTUATOR_MONITOR("/actuator/**", "monitor监控不校验"),
    SWAGGER_API_DOC("/v2/api-docs/**", "swagger文档"),
    static_path("/static/**", "static"),
    test_path("/test/**", "test_path"),
    websocket_path("/websocket/**", "websocket_path"),
    ;

    private final String url;
    private final String desc;

    ShiroWhiteList(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }
}
