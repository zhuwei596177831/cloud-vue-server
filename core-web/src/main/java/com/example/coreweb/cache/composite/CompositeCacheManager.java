package com.example.coreweb.cache.composite;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author 朱伟伟
 * @date 2022-02-17 17:23:24
 * @description
 */
public class CompositeCacheManager implements CacheManager {

    private final CaffeineCacheManager caffeineCacheManager;
    private final RedisCacheManager redisCacheManager;

    public CompositeCacheManager(CaffeineCacheManager caffeineCacheManager, RedisCacheManager redisCacheManager) {
        this.caffeineCacheManager = caffeineCacheManager;
        this.redisCacheManager = redisCacheManager;
    }

    @Override
    public Cache getCache(String name) {
        CaffeineCache caffeineCache = null;
        RedisCache redisCache = null;
        Cache caffeineC = caffeineCacheManager.getCache(name);
        if (caffeineC != null) {
            caffeineCache = (CaffeineCache) caffeineC;
        }
        Cache redisC = redisCacheManager.getCache(name);
        if (redisC != null) {
            redisCache = (RedisCache) redisC;
        }
        return new CompositeCache(caffeineCache, redisCache);
    }

    @Override
    public Collection<String> getCacheNames() {
        Collection<String> cacheNames = caffeineCacheManager.getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNames)) {
            return cacheNames;
        }
        cacheNames = redisCacheManager.getCacheNames();
        return cacheNames;
    }
}
