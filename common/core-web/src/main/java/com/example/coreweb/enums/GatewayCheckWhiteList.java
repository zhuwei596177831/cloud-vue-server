package com.example.coreweb.enums;

import lombok.Getter;

/**
 * @author 朱伟伟
 * @date 2021-06-01 15:27:12
 * @description 校验是否是网关路由过来的请求的白名单
 */
@Getter
public enum GatewayCheckWhiteList {
    websocket_path("/websocket/**", "websocket链接不校验"),
    test_path("/test/**", "test_path"),
    static_path("/static/**", "static"),
    ACTUATOR_MONITOR("/actuator/**", "monitor监控不校验"),
    SWAGGER_API_DOC("/v2/api-docs/**", "swagger文档不校验"),
    ;

    private final String url;
    private final String desc;

    GatewayCheckWhiteList(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }
}
