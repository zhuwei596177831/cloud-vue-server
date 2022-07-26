package com.example.coreweb.cache.redis;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * @author 朱伟伟
 * @date 2022-02-15 16:01:34
 * @description 只使用redis时自定义jackson序列化配置
 * @see org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration
 * {@link org.springframework.boot.autoconfigure.cache.CacheConfigurations}
 * @see RedisCacheConfiguration
 * @see org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
 * {@link org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration}
 */
//@Configuration(proxyBeanMethods = false)
public class SingleRedisConfiguration {

    @Bean
    public JacksonRedisTemplate jacksonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new JacksonRedisTemplate(redisConnectionFactory);
    }

    /**
     * 自定义RedisCacheConfiguration使用JacksonRedisTemplate做缓存数据的序列化
     *
     * @param cacheProperties:
     * @param jacksonRedisTemplate:
     * @author: 朱伟伟
     * @date: 2022-02-15 17:02
     **/
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties,
                                                           JacksonRedisTemplate jacksonRedisTemplate) {
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jacksonRedisTemplate.getValueSerializer()));
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

}
