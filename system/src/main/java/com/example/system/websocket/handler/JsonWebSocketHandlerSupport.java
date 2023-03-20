package com.example.system.websocket.handler;

import com.example.coreweb.websocket.TextWebSocketHandlerSupport;
import com.example.system.applicationevent.WSJsonApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2022-09-23 17:01:37
 * @description
 */
@Component
public class JsonWebSocketHandlerSupport extends TextWebSocketHandlerSupport implements ApplicationListener<WSJsonApplicationEvent> {
    @Override
    public void onApplicationEvent(WSJsonApplicationEvent event) {
        getQueue().offer(event.getWsJson());
    }
}
