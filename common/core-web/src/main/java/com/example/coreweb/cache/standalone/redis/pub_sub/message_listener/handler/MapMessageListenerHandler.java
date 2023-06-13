package com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.handler;

import com.example.coreweb.cache.standalone.redis.pub_sub.annotation.RedisTopic;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2023-06-12 17:25:29
 */
@RedisTopic(channel = "mapHandler")
@Component
public class MapMessageListenerHandler implements MessageListenerHandler<Map<String, Object>> {

    @Override
    public void handleMessage(Map<String, Object> message, String channel) {
        System.out.println("收到发布的message：");
        System.out.println("1、channel：" + channel);
        System.out.println("2、body：" + message.toString());
    }

}
