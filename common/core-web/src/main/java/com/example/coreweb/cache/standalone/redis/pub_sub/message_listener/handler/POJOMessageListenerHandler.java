package com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.handler;

import com.example.core.entity.Json;
import com.example.coreweb.cache.standalone.redis.pub_sub.annotation.RedisTopic;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2023-06-12 17:45:16
 */
@RedisTopic(channel = "POJOHandler")
@Component
public class POJOMessageListenerHandler implements MessageListenerHandler<Json> {

    @Override
    public void handleMessage(Json message, String channel) {
        System.out.println("收到发布的message：");
        System.out.println("1、channel：" + channel);
        System.out.println("2、body：" + message.toString());
    }

}
