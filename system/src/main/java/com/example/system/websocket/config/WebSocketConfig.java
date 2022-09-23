package com.example.system.websocket.config;

import com.example.system.websocket.handler.JsonWebSocketHandler;
import com.example.system.websocket.handler.TestWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator;

/**
 * @author 朱伟伟
 * @date 2022-09-22 09:48:59
 * @description
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private JsonWebSocketHandler jsonWebSocketHandler;
    @Autowired
    private TestWebSocketHandler testWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ExceptionWebSocketHandlerDecorator(jsonWebSocketHandler), "/websocket/json").setAllowedOrigins("*");
        registry.addHandler(new ExceptionWebSocketHandlerDecorator(testWebSocketHandler), "/websocket/test").setAllowedOrigins("*");
    }

}
