package com.example.gateway.web.exception;

import com.example.core.entity.Json;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author 朱伟伟
 * @date 2022-09-27 13:53:06
 * @description webflux 全局异常处理
 * @see org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler
 * @see org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler
 */
@Component
@Order(-10)
public class GlobalWebExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ex.printStackTrace();
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer dataBuffer = response.bufferFactory().wrap(Json.fail(ex.getMessage()).toString().getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

}
