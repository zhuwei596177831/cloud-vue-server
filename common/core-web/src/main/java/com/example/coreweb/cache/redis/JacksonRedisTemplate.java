package com.example.coreweb.cache.redis;

import com.example.core.util.ObjectMapperUtil;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author 朱伟伟
 * @date 2022-02-15 16:04:21
 * @description
 */
public class JacksonRedisTemplate extends RedisTemplate<String, Object> {

    public JacksonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(ObjectMapperUtil.instance());
        setKeySerializer(RedisSerializer.string());
        setValueSerializer(jackson2JsonRedisSerializer);
        setHashKeySerializer(RedisSerializer.string());
        setHashValueSerializer(jackson2JsonRedisSerializer);
        setConnectionFactory(redisConnectionFactory);
    }

}
