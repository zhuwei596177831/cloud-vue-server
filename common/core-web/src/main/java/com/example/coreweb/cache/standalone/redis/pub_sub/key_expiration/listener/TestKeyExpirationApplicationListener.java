package com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;

/**
 *
 * @author 朱伟伟
 * @date 2023-06-13 11:34:43
 */
//@Component
public class TestKeyExpirationApplicationListener implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return RedisKeyExpiredEvent.class.isAssignableFrom(eventType);
    }

    @SuppressWarnings("all")
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        RedisKeyExpiredEvent expiredEvent = (RedisKeyExpiredEvent) event;
        System.out.println("收到key失效事件message：");
        System.out.println("key：" + new String(expiredEvent.getSource()));
    }
}
