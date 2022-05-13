package com.example.gateway.filter.globalfilter;

import com.example.core.util.Constants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author 朱伟伟
 * @date 2021-05-18 16:27:59
 * @description 从gateway路由到下游前添加校验参数
 */
@Component
public class GatewayTokenSetFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        List<String> gatewayTokens = headers.getOrEmpty(Constants.GATEWAY_TOKEN);
        if (gatewayTokens.isEmpty()) {
            long time = Instant.now().toEpochMilli();
            request.mutate().header(Constants.GATEWAY_TIME, String.valueOf(time));
            String nonce = UUID.randomUUID().toString();
            request.mutate().header(Constants.GATEWAY_NONCE, nonce);
            String GATEWAY_TOKEN = DigestUtils.md5DigestAsHex((time + Constants.GATEWAY_SIGN_KEY + nonce).getBytes());
            request.mutate().header(Constants.GATEWAY_TOKEN, GATEWAY_TOKEN);
        }
        return chain.filter(exchange);
    }

}
