package com.example.gateway.filter.globalFilter;

import com.alibaba.fastjson.JSONObject;
import com.example.core.util.ConstantsHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author 朱伟伟
 * @date 2021-05-18 16:27:59
 * @description set token before send to downstream
 */
@Component
public class GatewayTokenSetFilter implements GlobalFilter, ApplicationRunner {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        List<String> gatewayTokens = headers.getOrEmpty(ConstantsHolder.GATEWAY_TOKEN);
        if (gatewayTokens.isEmpty()) {
            String gatewaySignKey;
            try {
                gatewaySignKey = stringRedisTemplate.boundValueOps(ConstantsHolder.GATEWAY_SIGN_KEY).get();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("get gateway-signKey token error", e);
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                return response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(getTokenErrorData()))));
            }
            long time = Instant.now().toEpochMilli();
            request.mutate().header(ConstantsHolder.GATEWAY_TIME, String.valueOf(time));
            String nonce = UUID.randomUUID().toString();
            request.mutate().header(ConstantsHolder.GATEWAY_NONCE, nonce);
            request.mutate().header(ConstantsHolder.GATEWAY_TOKEN, DigestUtils.md5DigestAsHex((time + gatewaySignKey + nonce).getBytes()));
        }
        return chain.filter(exchange);
    }

    public byte[] getTokenErrorData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        jsonObject.put("msg", "get gateway-signKey token error");
        return jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        stringRedisTemplate.opsForValue().set(ConstantsHolder.GATEWAY_SIGN_KEY, UUID.randomUUID().toString());
    }
}
