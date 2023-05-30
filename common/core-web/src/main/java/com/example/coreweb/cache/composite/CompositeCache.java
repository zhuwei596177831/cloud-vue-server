package com.example.coreweb.cache.composite;

import org.springframework.cache.Cache;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author 朱伟伟
 * @date 2022-02-17 17:26:01
 * @description 多级缓存
 */
public class CompositeCache implements Cache {

    //初始化布隆过滤器
//1000：期望存入的数据个数，0.001：期望的误差率
//    BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 10000000, 0.001F);


    private final List<Cache> caches = new ArrayList<>();

    public CompositeCache() {
    }

    public CompositeCache(Cache... caches) {
        setCaches(Arrays.asList(caches));
    }

    public void setCaches(Collection<Cache> caches) {
        this.caches.addAll(caches);
    }

    @Override
    public String getName() {
        String name = null;
        for (Cache cache : caches) {
            name = cache.getName();
            if (StringUtils.hasLength(name)) {
                break;
            }
        }
        return name;
    }

    @Override
    public Object getNativeCache() {
        Object nativeCache = null;
        for (Cache cache : caches) {
            nativeCache = cache.getNativeCache();
            break;
        }
        return nativeCache;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper valueWrapper = null;
        for (int i = 0; i < caches.size(); i++) {
            valueWrapper = caches.get(i).get(key);
            if (valueWrapper != null) {
                if (i != 0) {
                    refreshPreviousCache(i - 1, key, valueWrapper);
                }
                break;
            }
        }
        return valueWrapper;
    }

    private void refreshPreviousCache(int index, Object key, Object result) {
        for (int i = index; i >= 0; i--) {
            this.caches.get(i).put(key, result instanceof ValueWrapper ? ((ValueWrapper) result).get() : result);
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        T result = null;
        for (int i = 0; i < caches.size(); i++) {
            result = caches.get(i).get(key, type);
            if (result != null) {
                if (i != 0) {
                    refreshPreviousCache(i - 1, key, result);
                }
                break;
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings({"all"})
    public <T> T get(Object key, Callable<T> valueLoader) {
        boolean found = false;
        T result = null;
        for (int i = 0; i < caches.size(); i++) {
            ValueWrapper valueWrapper = caches.get(i).get(key);
            if (valueWrapper != null) {
                result = (T) valueWrapper.get();
                found = true;
                if (i == 0) {
                    break;
                } else {
                    refreshPreviousCache(i - 1, key, result);
                    break;
                }
            }
        }
        if (!found) {
            try {
                result = valueLoader.call();
                refreshPreviousCache(this.caches.size() - 1, key, result);
            } catch (Exception e) {
                throw new ValueRetrievalException(key, valueLoader, e);
            }
        }
        return result;
    }

    @Override
    public void put(Object key, Object value) {
        for (Cache cache : caches) {
            cache.put(key, value);
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
        for (Cache cache : caches) {
            cache.evict(key);
        }
    }

    @Override
    public boolean evictIfPresent(Object key) {
        for (Cache cache : caches) {
            cache.evictIfPresent(key);
        }
        return false;
    }

    @Override
    public void clear() {
        for (Cache cache : caches) {
            cache.clear();
        }
    }

    @Override
    public boolean invalidate() {
        for (Cache cache : caches) {
            cache.invalidate();
        }
        return false;
    }
}
