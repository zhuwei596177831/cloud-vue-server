package com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.coreweb.cache.standalone.redis.pub_sub.annotation.RedisTopic;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2023-06-12 17:40:49
 */
@RedisTopic(channel = "JSONObjectHandler")
@Component
public class JSONObjectMessageListenerHandler implements MessageListenerHandler<JSONObject> {

    @Override
    public void handleMessage(JSONObject message, String channel) {
        System.out.println("收到发布的message：");
        System.out.println("1、channel：" + channel);
        System.out.println("2、body：" + message.toString());
    }

}
