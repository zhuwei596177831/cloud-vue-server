package com.example.shiroAuthencation.sessioncache;

import org.apache.shiro.session.Session;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2021-05-24 11:40:53
 * @description 自定义 RedisSessionTemplate
 */
public class RedisSessionTemplate extends RedisTemplate<Serializable, Session> {

    public RedisSessionTemplate(RedisConnectionFactory redisConnectionFactory) {
        setKeySerializer(RedisSerializer.string());
        setValueSerializer(RedisSerializer.java());
        setHashKeySerializer(RedisSerializer.string());
        setHashValueSerializer(RedisSerializer.java());
        setConnectionFactory(redisConnectionFactory);
    }

}
