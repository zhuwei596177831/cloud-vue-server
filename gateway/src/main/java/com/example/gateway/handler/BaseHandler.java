package com.example.gateway.handler;

import com.example.core.entity.Json;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2022-09-21 11:15:25
 * @description
 */
public abstract class BaseHandler {

    private <T> Mono<ServerResponse> toResponse(Publisher<T> body, Class<T> elementClass) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(body, elementClass)
                ;
    }

    protected Mono<ServerResponse> toResponse(Mono<Json> mono) {
        return toResponse(mono, Json.class);
    }

    protected <T> Mono<ServerResponse> toResponse(Publisher<T> publisher) {
        return toResponse(toMonoJson(publisher), Json.class);
    }

    /**
     * Publisher<T>转换为Mono<Json>，统一数据格式
     *
     * @param publisher:
     * @author: 朱伟伟
     * @date: 2022-09-21 11:35
     **/
    @SuppressWarnings({"unchecked"})
    private <T> Mono<Json> toMonoJson(Publisher<T> publisher) {
        if (publisher instanceof Flux) {
            Flux<T> flux = (Flux<T>) publisher;
            return flux.reduce(Json.success(), (json, p) -> {
                List<T> data = json.getData() == null ? new LinkedList<>() : (List<T>) json.getData();
                data.add(p);
                json.setData(data);
                return json;
            });
        } else if (publisher instanceof Mono) {
            Mono<T> mono = (Mono<T>) publisher;
            return mono.map(Json::ok);
        } else {
            return Mono.empty();
        }
    }

}
