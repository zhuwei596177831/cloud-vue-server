package com.example.coreweb.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.core.entity.Json;
import com.example.core.util.ObjectMapperUtil;
import com.example.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
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
 * @date 2022-09-23 16:55:09
 * @description
 */
public abstract class GenericWebSocketHandler extends TextWebSocketHandler implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BlockingQueue<Object> queue = new LinkedBlockingQueue<>();

    private final Map<String, WebSocketSessionDecorator> webSocketSessionMap = new ConcurrentHashMap<>(64);

    public Map<String, WebSocketSessionDecorator> getWebSocketSessionMap() {
        return webSocketSessionMap;
    }

    protected BlockingQueue<Object> getQueue() {
        return queue;
    }

    public boolean offer(Object o) {
        return getQueue().offer(o);
    }

    @Override
    public final void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("webSocketSession sessionId {} established", session.getId());
        webSocketSessionMap.put(session.getId(), new WebSocketSessionDecorator(session));
        doAfterConnectionEstablished(session);
    }

    protected void doAfterConnectionEstablished(WebSocketSession session) {

    }

    @Override
    public final void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("webSocketSession sessionId {} closed", session.getId());
        webSocketSessionMap.remove(session.getId());
        doAfterConnectionClosed(session, status);
    }

    protected void doAfterConnectionClosed(WebSocketSession session, CloseStatus status) {

    }

    @Override
    protected final void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("接收：{}", payload);
        JSONObject object = JSON.parseObject(payload, JSONObject.class);
        String name = object.getString("name");
        if (StringUtils.hasText(name) && name.equals("zhuweiwei")) {
            WebSocketSessionDecorator webSocketSessionDecorator = webSocketSessionMap.get(session.getId());
            if (webSocketSessionDecorator != null) {
                webSocketSessionDecorator.setAuthenticated(true);
            }
        } else {
            String send = Json.fail("鉴权失败").toString();
            logger.info("发送：{}", send);
            session.sendMessage(new TextMessage(send));
            webSocketSessionMap.remove(session.getId());
            session.close();
        }
    }

    @Override
    public final void run(String... args) throws Exception {
        //启动守护线程向认证通过、连接存活的WebSocketSession推送数据
        Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r, getClass().getName() + "-Daemon-Thread");
            thread.setDaemon(true);
            return thread;
        }).execute(() -> {
            for (; ; ) {
                try {
                    Object value = this.queue.take();
                    String message = ObjectMapperUtil.instance().writeValueAsString(value);
                    logger.info("从队列取得数据：{}", message);
                    if (!webSocketSessionMap.isEmpty()) {
                        for (WebSocketSessionDecorator session : webSocketSessionMap.values()) {
                            if (session.isAuthenticated() && session.isOpen()) {
                                session.sendMessage(new TextMessage(message));
                            } else {
                                webSocketSessionMap.remove(session.getId());
                                session.close();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
