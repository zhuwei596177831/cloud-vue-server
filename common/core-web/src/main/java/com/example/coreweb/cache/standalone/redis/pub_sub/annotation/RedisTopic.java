package com.example.coreweb.cache.standalone.redis.pub_sub.annotation;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，对应Redis Topic
 *
 * @author: 朱伟伟
 * @date: 2022-03-19 00:04
 * @see com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.config.RedisMessageListenerContainerConfig
 * @see com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.config.MessageListenerAdapterConfig
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisTopic {

    /**
     * 通道的名称，（支持模式匹配）
     */
    String channel();

    /**
     * 通道类型
     *
     * @see ChannelTopic
     * @see org.springframework.data.redis.listener.PatternTopic
     */
    Class<? extends Topic> topic() default ChannelTopic.class;

}
