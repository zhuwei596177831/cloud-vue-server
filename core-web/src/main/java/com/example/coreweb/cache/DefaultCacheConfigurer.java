package com.example.coreweb.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * @author 朱伟伟
 * @date 2022-02-15 14:20:24
 * @description 配置默认缓存
 */
@EnableCaching(proxyTargetClass = true)
@Configuration(proxyBeanMethods = false)
public class DefaultCacheConfigurer extends CachingConfigurerSupport {

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public CacheManager cacheManager() {
        //return super.cacheManager();
        return redisCacheManager;
    }

    @Override
    public CacheResolver cacheResolver() {
        return super.cacheResolver();
    }

    @Override
    public KeyGenerator keyGenerator() {
        return super.keyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return super.errorHandler();
    }
}
