package com.example.shiroAuthencation.sessioncache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @author 朱伟伟
 * @date 2021-05-24 13:24:20
 * @description 自定义CacheManager实现类，用来给各个模块共享shiro的session
 * @see org.apache.shiro.session.mgt.eis.CachingSessionDAO
 */
public class ShiroReisCacheManager implements CacheManager {

    private final ShiroReisCache shiroReisCache;

    public ShiroReisCacheManager(ShiroReisCache shiroReisCache) {
        this.shiroReisCache = shiroReisCache;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return (Cache<K, V>) shiroReisCache;
    }
}
