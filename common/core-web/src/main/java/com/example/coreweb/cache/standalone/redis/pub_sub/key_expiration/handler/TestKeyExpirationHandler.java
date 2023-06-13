package com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.handler;

import com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.KeyExpiration;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2023-06-13 10:49:25
 */
@Component
public class TestKeyExpirationHandler implements KeyExpirationHandler {

    @Override
    public boolean support(Message message) {
        return KeyExpiration.test.getKey().equals(new String(message.getBody()));
    }

    @Override
    public void handle(Message message) {
        System.out.println("收到key失效事件message：");
        System.out.println("1、channel：" + new String(message.getChannel()));
        System.out.println("2、key：" + new String(message.getBody()));
    }

}
