package com.example.coreweb.cache.composite;

import com.example.coreweb.cache.redis.JacksonRedisTemplate;
import com.example.coreweb.cache.util.CacheConstants;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
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
 * 多级缓存配置
 * </p>
 * 当前：Caffeine一级内存缓存，redis二级远程缓存
 *
 * @author 朱伟伟
 * @date 2023-05-30 10:39
 **/
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties.class)
public class CompositeCacheConfig {

    /**
     * 配置使用自定义{@link com.fasterxml.jackson.databind.ObjectMapper}的RedisTemplate
     *
     * @param redisConnectionFactory Redis连接的线程安全工厂。
     * @author 朱伟伟
     * @date 2023-05-30 10:40
     **/
    @Bean
    public JacksonRedisTemplate jacksonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new JacksonRedisTemplate(redisConnectionFactory);
    }

    /**
     * 创建复合{@link org.springframework.cache.CacheManager}
     *
     * @param cacheProperties      缓存属性
     * @param caffeineCacheManager caffeine对应的CacheManager
     * @param redisCacheManager    redis对应的CacheManager
     * @author 朱伟伟
     * @date 2023-05-30 10:36
     **/
    @Bean(name = CacheConstants.COMPOSITE_CACHE_MANAGER_NAME)
    public CompositeCacheManager compositeCacheManager(CacheProperties cacheProperties,
                                                       CaffeineCacheManager caffeineCacheManager,
                                                       RedisCacheManager redisCacheManager) {
        return new CompositeCacheManager(caffeineCacheManager, redisCacheManager);
    }

    /**
     * 创建{@link CaffeineCacheManager}
     *
     * @param cacheProperties 缓存属性
     * @author 朱伟伟
     * @date 2023-05-30 10:25
     **/
    @Bean(name = CacheConstants.CAFFEINE_CACHE_MANAGER_NAME)
    public CaffeineCacheManager caffeineCacheManager(CacheProperties cacheProperties) {
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
     * 创建{@link RedisCacheManager}
     *
     * @param cacheProperties:
     * @param jacksonRedisTemplate:
     * @param redisConnectionFactory:
     * @author: 朱伟伟
     * @date: 2022-02-17 20:46
     * @see org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager#decorateCache(Cache)
     * @see org.springframework.cache.transaction.TransactionAwareCacheDecorator
     * @see org.springframework.cache.transaction.TransactionAwareCacheManagerProxy
     **/
    @Bean(name = CacheConstants.REDIS_CACHE_MANAGER_NAME)
    public RedisCacheManager redisCacheManager(CacheProperties cacheProperties,
                                               JacksonRedisTemplate jacksonRedisTemplate,
                                               RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = createRedisCacheConfiguration(cacheProperties, jacksonRedisTemplate);
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration);
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
        }
        //启用RedisCaches以同步正在进行的spring管理事务的缓存放入/退出操作
        builder.transactionAware();
        return builder.build();
    }

    /**
     * 自定义RedisCacheConfiguration定制化注解方式缓存配置，使用{@link JacksonRedisTemplate}实现数据的序列化和反序列化
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
