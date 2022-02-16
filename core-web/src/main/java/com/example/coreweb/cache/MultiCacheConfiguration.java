package com.example.coreweb.cache;

import com.example.coreweb.cache.redis.JacksonRedisTemplate;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ResourceCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2022-02-16 11:23:44
 * @description 多级缓存配置 Ehcache做一级本地缓存 redis远程缓存
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties.class)
public class MultiCacheConfiguration {

    @Bean
    public JacksonRedisTemplate jacksonRedisTemplate(ObjectProvider<RedisConnectionFactory> redisConnectionFactory) {
        return new JacksonRedisTemplate(redisConnectionFactory.getObject());
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(RedisConnectionFactory.class)
    public static class RedisCacheConfiguration {

        /**
         * 自定义RedisCacheConfiguration用于缓存注解方式 缓存数据的序列化
         *
         * @param cacheProperties:
         * @param jacksonRedisTemplate:
         * @author: 朱伟伟
         * @date: 2022-02-15 17:02
         **/
        @Bean
        public org.springframework.data.redis.cache.RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties, JacksonRedisTemplate jacksonRedisTemplate) {
            CacheProperties.Redis redisProperties = cacheProperties.getRedis();
            org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig();
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

        @Bean
            //@Primary
        RedisCacheManager redisCacheManager(CacheProperties cacheProperties,
                                            org.springframework.data.redis.cache.RedisCacheConfiguration redisCacheConfiguration,
                                            ObjectProvider<RedisCacheManagerBuilderCustomizer> redisCacheManagerBuilderCustomizers,
                                            ObjectProvider<RedisConnectionFactory> redisConnectionFactory) {
            RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory.getObject())
                    .cacheDefaults(redisCacheConfiguration);
            List<String> cacheNames = cacheProperties.getCacheNames();
            if (!cacheNames.isEmpty()) {
                builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
            }
            redisCacheManagerBuilderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
            return builder.build();
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass({Cache.class, EhCacheCacheManager.class})
    @Conditional({EhCacheCacheConfiguration.ConfigAvailableCondition.class})
    public static class EhCacheCacheConfiguration {

        @Bean
        EhCacheCacheManager ehCacheCacheManager(CacheManager ehCacheManager) {
            return new EhCacheCacheManager(ehCacheManager);
        }

        @Bean
        net.sf.ehcache.CacheManager ehCacheManager(CacheProperties cacheProperties) {
            Resource location = cacheProperties.resolveConfigLocation(cacheProperties.getEhcache().getConfig());
            if (location != null) {
                return EhCacheManagerUtils.buildCacheManager(location);
            }
            return EhCacheManagerUtils.buildCacheManager();
        }

        /**
         * Determine if the EhCache configuration is available. This either kick in if a
         * default configuration has been found or if property referring to the file to use
         * has been set.
         */
        static class ConfigAvailableCondition extends ResourceCondition {

            ConfigAvailableCondition() {
                super("EhCache", "spring.cache.ehcache.config", "classpath:/ehcache.xml");
            }

        }

    }


}
