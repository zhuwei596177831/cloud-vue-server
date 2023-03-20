package com.example.coreweb.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 朱伟伟
 * @date 2022-09-23 16:46:00
 * @description
 */
@Component
public class WebSocketSessionValidator implements CommandLineRunner {

    @Autowired(required = false)
    private Collection<TextWebSocketHandlerSupport> textWebSocketHandlerSupports;

    @Override
    public void run(String... args) throws Exception {
        if (!CollectionUtils.isEmpty(textWebSocketHandlerSupports)) {
            //启动守护线程定时扫描webSocketSessionMap，清除和关闭未认证的WebSocketSession，
            // 延迟5秒间隔60秒自动执行
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("WebSocketSession-Check-Thread");
                return thread;
            }).scheduleWithFixedDelay(() -> {
                try {
                    for (TextWebSocketHandlerSupport textWebSocketHandlerSupport : textWebSocketHandlerSupports) {
                        Map<String, WebSocketSessionDecorator> myWebSocketSessionMap = textWebSocketHandlerSupport.getWebSocketSessionMap();
                        for (WebSocketSessionDecorator session : myWebSocketSessionMap.values()) {
                            try {
                                if (!session.isAuthenticated()) {
                                    myWebSocketSessionMap.remove(session.getId());
                                    session.close();
                                } else if (!session.isOpen()) {
                                    myWebSocketSessionMap.remove(session.getId());
                                }
                            } catch (Throwable ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }, 5, 60, TimeUnit.SECONDS);
        }
    }
}
