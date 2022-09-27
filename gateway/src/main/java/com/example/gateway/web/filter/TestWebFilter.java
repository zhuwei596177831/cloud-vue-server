package com.example.gateway.web.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author 朱伟伟
 * @date 2022-09-27 10:39:28
 * @description webflux 过滤器
 */
@Component
public class TestWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //ServerHttpResponse response = exchange.getResponse();
        //DataBuffer dataBuffer = response.bufferFactory().wrap(Json.fail("认证失败").toString().getBytes());
        //return response.writeWith(Mono.just(dataBuffer));
        return chain.filter(exchange);
    }
}
