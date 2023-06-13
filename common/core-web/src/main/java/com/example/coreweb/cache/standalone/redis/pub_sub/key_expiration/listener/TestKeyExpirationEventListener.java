package com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.listener;

import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;

/**
 * @author 朱伟伟
 * @date 2023-06-13 14:01:56
 */
//@Component
public class TestKeyExpirationEventListener {

    @SuppressWarnings("all")
    @EventListener
    public void testKeyExpiration(RedisKeyExpiredEvent redisKeyExpiredEvent) {
        System.out.println("收到key失效事件message：");
        System.out.println("key：" + new String(redisKeyExpiredEvent.getSource()));
    }

}
