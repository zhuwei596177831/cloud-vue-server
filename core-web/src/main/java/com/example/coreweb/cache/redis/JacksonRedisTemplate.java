package com.example.coreweb.cache.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author 朱伟伟
 * @date 2022-02-15 16:04:21
 * @description
 */
public class JacksonRedisTemplate extends RedisTemplate<String, Object> {

    public JacksonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(createJacksonObjectMapper());
        setKeySerializer(RedisSerializer.string());
        setValueSerializer(jackson2JsonRedisSerializer);
        setHashKeySerializer(RedisSerializer.string());
        setHashValueSerializer(jackson2JsonRedisSerializer);
        setConnectionFactory(redisConnectionFactory);
        afterPropertiesSet();
    }

    private ObjectMapper createJacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //设置时区 东八区
//        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        //date类型 全局配置格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //配置 java8的支持
        objectMapper.registerModule(new JavaTimeModule());
        // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return objectMapper;
    }

}
