package com.example.coreweb.cache.composite;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;

import java.util.*;

/**
 * 复合{@link CacheManager}实现
 *
 * @author 朱伟伟
 * @date 2022-02-17 17:23:24
 * @description
 */
public class CompositeCacheManager implements CacheManager, InitializingBean {

    private final List<CacheManager> cacheManagers = new ArrayList<>();

    private boolean fallbackToNoOpCache = false;

    public CompositeCacheManager() {
    }

    public CompositeCacheManager(CacheManager... cacheManagers) {
        setCacheManagers(Arrays.asList(cacheManagers));
    }

    public void setCacheManagers(Collection<CacheManager> cacheManagers) {
        this.cacheManagers.addAll(cacheManagers);
    }

    public void setFallbackToNoOpCache(boolean fallbackToNoOpCache) {
        this.fallbackToNoOpCache = fallbackToNoOpCache;
    }

    @Override
    public Cache getCache(String name) {
        return createCache(name);
    }

    private Cache createCache(String name) {
        List<Cache> caches = new ArrayList<>();
        for (CacheManager cacheManager : cacheManagers) {
            Cache cache = cacheManager.getCache(name);
            caches.add(cache);
        }
        return new CompositeCache(caches.toArray(new Cache[0]));
    }

    @Override
    public Collection<String> getCacheNames() {
        Set<String> names = new LinkedHashSet<>();
        for (CacheManager manager : this.cacheManagers) {
            names.addAll(manager.getCacheNames());
        }
        return Collections.unmodifiableSet(names);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (fallbackToNoOpCache) {
            this.cacheManagers.add(new NoOpCacheManager());
        }
    }
}
