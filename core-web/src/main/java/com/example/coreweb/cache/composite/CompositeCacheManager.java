package com.example.coreweb.cache.composite;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 朱伟伟
 * @date 2022-02-17 17:23:24
 * @description
 */
public class CompositeCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    private final CaffeineCacheManager caffeineCacheManager;
    private final RedisCacheManager redisCacheManager;

    public CompositeCacheManager(CaffeineCacheManager caffeineCacheManager, RedisCacheManager redisCacheManager) {
        this.caffeineCacheManager = caffeineCacheManager;
        this.redisCacheManager = redisCacheManager;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache == null) {
            synchronized (this.cacheMap) {
                cache = cacheMap.get(name);
                if (cache == null) {
                    cache = createCache(name);
                    this.cacheMap.put(name, cache);
                }
            }
        }
        return cache;
    }

    private Cache createCache(String name) {
        CaffeineCache caffeineCache = null;
        RedisCache redisCache = null;
        Cache caffeineC = this.caffeineCacheManager.getCache(name);
        if (caffeineC != null) {
            caffeineCache = (CaffeineCache) caffeineC;
        }
        Cache redisC = this.redisCacheManager.getCache(name);
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
