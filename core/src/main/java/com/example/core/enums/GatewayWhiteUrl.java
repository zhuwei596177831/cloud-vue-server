package com.example.core.enums;

import lombok.Getter;

/**
 * @author 朱伟伟
 * @date 2021-06-01 15:27:12
 * @description 权限过滤白名单
 */
@Getter
public enum GatewayWhiteUrl {
    SYSTEM_USER_FEIGN("/openFeign/**", "openfeign调用不校验"),
    SWAGGER_API_DOC("/v2/api-docs/**", "swagger文档"),
    ;
    private final String url;
    private final String desc;

    GatewayWhiteUrl(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }
}
