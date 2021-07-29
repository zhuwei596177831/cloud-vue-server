package com.example.gateway.cors;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 朱伟伟
 * @date 2021-05-22 23:37:24
 * @description cors filter（跨域） 此方案 不行
 */
//@Component
public class CorsFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println(exchange.getRequest().getMethodValue());
//        ServerHttpResponse response = exchange.getResponse();
//        HttpHeaders headers = response.getHeaders();
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://127.0.0.1:9090");
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, OPTIONS, DELETE");
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
//        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
//        headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
//        ServerHttpRequest request = exchange.getRequest();
//        if (request.getMethod() == HttpMethod.OPTIONS) {
//            response.setStatusCode(HttpStatus.OK);
//            return Mono.empty();
//        }
        return chain.filter(exchange);
    }
}
