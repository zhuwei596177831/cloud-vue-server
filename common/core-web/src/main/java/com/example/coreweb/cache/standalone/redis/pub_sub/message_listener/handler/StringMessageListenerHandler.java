package com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.handler;

import com.example.coreweb.cache.standalone.redis.pub_sub.annotation.RedisTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2023-06-12 16:26:46
 */
@RedisTopic(channel = "stringHandler")
@Component
public class StringMessageListenerHandler implements MessageListenerHandler<String> {

    @Value("${server.port}")
    private Integer port;

    @Override
    public void handleMessage(String message, String channel) {
        System.out.println("当前服务端口号：" + port);
        System.out.println("收到发布的message：");
        System.out.println("1、channel：" + channel);
        System.out.println("2、body：" + message);
    }
}
