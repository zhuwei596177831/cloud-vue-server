package com.example.coreweb.cache.composite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

/**
 * @author 朱伟伟
 * @date 2022-02-15 14:20:24
 * @description 配置默认缓存
 */
@EnableCaching(proxyTargetClass = true)
@Configuration(proxyBeanMethods = false)
public class CompositeCacheConfigurer extends CachingConfigurerSupport {

    @Autowired
    private CompositeCacheManager compositeCacheManager;

    @Override
    public CacheManager cacheManager() {
        return compositeCacheManager;
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
