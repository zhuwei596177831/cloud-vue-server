package com.example.coreweb.cache.multi;

import com.example.core.util.Constants;
import com.example.coreweb.cache.redis.JacksonRedisTemplate;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 朱伟伟
 * @date 2022-02-17 20:40:56
 * @description 多级缓存配置 Caffeine做一级内存缓存 redis二级远程缓存
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties.class)
public class MultiCacheConfig {

    @Bean
    public JacksonRedisTemplate jacksonRedisTemplate(ObjectProvider<RedisConnectionFactory> redisConnectionFactory) {
        return new JacksonRedisTemplate(redisConnectionFactory.getObject());
    }

    @Bean(name = Constants.multiCacheManagerName)
    public MultiCacheManager multiCacheManager(CacheProperties cacheProperties, RedisCacheManager redisCacheManager) {
        CaffeineCacheManager caffeineCacheManager = createCaffeineCacheManager(cacheProperties);
        return new MultiCacheManager(caffeineCacheManager, redisCacheManager);
    }

    private CaffeineCacheManager createCaffeineCacheManager(CacheProperties cacheProperties) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        String specification = cacheProperties.getCaffeine().getSpec();
        if (StringUtils.hasText(specification)) {
            caffeineCacheManager.setCacheSpecification(specification);
        } else {
            Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                    //指定缓存可能包含的最大条目数
                    .maximumSize(10_000)
                    //条目创建后时间自动删除
                    .expireAfterWrite(10, TimeUnit.MINUTES)
                    //条目在最后一次被访问后自动删除
                    //.expireAfterAccess(10, TimeUnit.SECONDS)
                    ;
            caffeineCacheManager.setCaffeine(caffeine);
        }
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNames)) {
            caffeineCacheManager.setCacheNames(cacheNames);
        }
        return caffeineCacheManager;
    }

    /**
     * 创建 RedisCacheManager
     *
     * @param cacheProperties:
     * @param jacksonRedisTemplate:
     * @param redisConnectionFactory:
     * @author: 朱伟伟
     * @date: 2022-02-17 20:46
     **/
    @Bean(name = Constants.redisCacheManagerName)
    public RedisCacheManager createRedisCacheManager(CacheProperties cacheProperties,
                                                     JacksonRedisTemplate jacksonRedisTemplate,
                                                     ObjectProvider<RedisConnectionFactory> redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = createRedisCacheConfiguration(cacheProperties, jacksonRedisTemplate);
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory.getObject())
                .cacheDefaults(redisCacheConfiguration);
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
        }
        return builder.build();
    }

    /**
     * 自定义RedisCacheConfiguration用于缓存注解方式 缓存数据的序列化
     *
     * @param cacheProperties:
     * @param jacksonRedisTemplate:
     * @author: 朱伟伟
     * @date: 2022-02-17 20:47
     **/
    private RedisCacheConfiguration createRedisCacheConfiguration(CacheProperties cacheProperties,
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
