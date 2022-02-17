package com.example.coreweb.cache.composite;

import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;

/**
 * @author 朱伟伟
 * @date 2022-02-17 17:26:01
 * @description 多级缓存
 */
public class CompositeCache implements Cache {

    private final CaffeineCache caffeineCache;
    private final RedisCache redisCache;

    public CompositeCache(CaffeineCache caffeineCache, RedisCache redisCache) {
        this.caffeineCache = caffeineCache;
        this.redisCache = redisCache;
    }

    @Override
    public String getName() {
        if (caffeineCache != null) {
            String name = caffeineCache.getName();
            if (!StringUtils.isEmpty(name)) {
                return name;
            }
        }
        if (redisCache != null) {
            String name = redisCache.getName();
            if (!StringUtils.isEmpty(name)) {
                return name;
            }
        }
        return null;
    }

    @Override
    public Object getNativeCache() {
        if (caffeineCache != null) {
            return caffeineCache.getNativeCache();
        }
        if (redisCache != null) {
            return redisCache.getNativeCache();
        }
        return null;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper valueWrapper;
        if (caffeineCache != null) {
            valueWrapper = caffeineCache.get(key);
            if (valueWrapper != null) {
                return valueWrapper;
            }
        }
        if (redisCache != null) {
            valueWrapper = redisCache.get(key);
            if (valueWrapper != null) {
                //刷新一级缓存
                if (caffeineCache != null) {
                    caffeineCache.put(key, valueWrapper.get());
                }
                return valueWrapper;
            }
        }
        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        T result;
        if (caffeineCache != null) {
            result = caffeineCache.get(key, type);
            if (result != null) {
                return result;
            }
        }
        if (redisCache != null) {
            result = redisCache.get(key, type);
            if (result != null) {
                //刷新一级缓存
                if (caffeineCache != null) {
                    caffeineCache.put(key, result);
                }
                return result;
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper valueWrapper;
        T result;
        if (caffeineCache != null) {
            valueWrapper = caffeineCache.get(key);
            if (valueWrapper != null) {
                return (T) valueWrapper.get();
            }
        }
        if (redisCache != null) {
            valueWrapper = redisCache.get(key);
            if (valueWrapper != null) {
                result = (T) valueWrapper.get();
                //刷新一级缓存
                if (caffeineCache != null) {
                    caffeineCache.put(key, valueWrapper.get());
                }
                return result;
            }
        }
        try {
            T call = valueLoader.call();
            if (caffeineCache != null) {
                caffeineCache.put(key, call);
            }
            if (redisCache != null) {
                redisCache.put(key, call);
            }
            return call;
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e);
        }
    }

    @Override
    public void put(Object key, Object value) {
        if (caffeineCache != null) {
            caffeineCache.put(key, value);
        }
        if (redisCache != null) {
            redisCache.put(key, value);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        ValueWrapper existingValue = get(key);
        if (existingValue == null) {
            put(key, value);
        }
        return existingValue;
    }

    @Override
    public void evict(Object key) {
        if (caffeineCache != null) {
            caffeineCache.evict(key);
        }
        if (redisCache != null) {
            redisCache.evict(key);
        }
    }

    @Override
    public boolean evictIfPresent(Object key) {
        if (caffeineCache != null) {
            caffeineCache.evictIfPresent(key);
        }
        if (redisCache != null) {
            redisCache.evictIfPresent(key);
        }
        return false;
    }

    @Override
    public void clear() {
        if (caffeineCache != null) {
            caffeineCache.clear();
        }
        if (redisCache != null) {
            redisCache.clear();
        }
    }

    @Override
    public boolean invalidate() {
        if (caffeineCache != null) {
            caffeineCache.invalidate();
        }
        if (redisCache != null) {
            redisCache.invalidate();
        }
        return false;
    }

}
