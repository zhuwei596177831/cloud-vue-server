package com.example.system.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.core.entity.Json;
import com.example.core.util.ObjectMapperUtil;
import com.example.core.util.StringUtils;
import com.example.system.applicationevent.JsonApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 朱伟伟
 * @date 2022-09-22 09:47:38
 * @description 监听JsonApplicationEvent事件、使用websocket推送json数据
 */
@Component
public class JsonWebSocketHandler extends TextWebSocketHandler implements
        ApplicationListener<JsonApplicationEvent>, CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BlockingQueue<Json> queue = new LinkedBlockingQueue<>();

    private final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("webSocketSession sessionId {} established", session.getId());
        webSocketSessionMap.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("webSocketSession sessionId {} closed", session.getId());
        webSocketSessionMap.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("接收：{}", payload);
        JSONObject object = JSON.parseObject(payload, JSONObject.class);
        String name = object.getString("name");
        if (StringUtils.hasText(name) && name.equals("zhuweiwei")) {
            String send = JSON.toJSONString(Json.ok(object.getString("code")));
            logger.info("发送：{}", send);
            session.sendMessage(new TextMessage(send));
        } else {
            String send = JSON.toJSONString(Json.fail("鉴权失败"));
            logger.info("发送：{}", send);
            session.sendMessage(new TextMessage(send));
            session.close();
        }
    }

    @Override
    public void onApplicationEvent(JsonApplicationEvent event) {
        this.queue.offer(event.getJson());
    }

    @Override
    public void run(String... args) throws Exception {
        Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r, "JsonWebSocketHandler-Thread");
            thread.setDaemon(true);
            return thread;
        }).execute(() -> {
            for (; ; ) {
                try {
                    Json json = this.queue.take();
                    logger.info("从队列取得json：{}", json.toString());
                    if (!webSocketSessionMap.isEmpty()) {
                        for (WebSocketSession webSocketSession : webSocketSessionMap.values()) {
                            webSocketSession.sendMessage(new TextMessage(ObjectMapperUtil.instance().writeValueAsString(json)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
