package com.example.shiroAuthencation.sessioncache;

import com.example.core.enums.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author 朱伟伟
 * @date 2021-05-24 10:47:14
 * @description 自定义cache 实现session持久化，用来多个服务共享shiro session
 */
@Slf4j
public class ShiroReisCache implements Cache<Serializable, Session> {

    private final ConcurrentMap<Serializable, Session> sessions = new ConcurrentHashMap<>();

    @Autowired
    private RedisSessionTemplate redisSessionTemplate;

    @Override
    public Session get(Serializable sessionId) throws CacheException {
        Session session = sessions.get(sessionId);
        if (session == null) {
            log.info("根据key【{}】从redis中获取session:", sessionId);
            Object redisSession = null;
            try {
                redisSession = redisSessionTemplate.opsForHash().get(RedisKey.SHIRO_SESSION_CACHE.getKey(), sessionId);
            } catch (Exception e) {
                log.info("根据key【{}】从redis中获取session异常:{}", sessionId, e.getMessage());
                e.printStackTrace();
            }
            if (redisSession != null) {
                session = (Session) redisSession;
                sessions.put(sessionId, session);
            }
        }
        return session;
    }

    @Override
    public Session put(Serializable sessionId, Session session) throws CacheException {
        redisSessionTemplate.opsForHash().put(RedisKey.SHIRO_SESSION_CACHE.getKey(), sessionId, session);
        sessions.put(sessionId, session);
        return session;
    }

    @Override
    public Session remove(Serializable sessionId) throws CacheException {
        redisSessionTemplate.opsForHash().delete(RedisKey.SHIRO_SESSION_CACHE.getKey(), sessionId);
        return sessions.remove(sessionId);
    }

    @Override
    public void clear() throws CacheException {
        redisSessionTemplate.opsForHash().getOperations().delete(RedisKey.SHIRO_SESSION_CACHE.getKey());
        sessions.clear();
    }

    @Override
    public int size() {
        if (!sessions.isEmpty()) {
            return sessions.size();
        }
        return redisSessionTemplate.opsForHash().keys(RedisKey.SHIRO_SESSION_CACHE.getKey()).size();
    }

    @Override
    public Set<Serializable> keys() {
        if (!sessions.isEmpty()) {
            return sessions.keySet();
        }
        try {
            return redisSessionTemplate.opsForHash().keys(RedisKey.SHIRO_SESSION_CACHE.getKey()).stream().map(o -> (Serializable) o).collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    @Override
    public Collection<Session> values() {
        if (!sessions.isEmpty()) {
            return sessions.values();
        }
        try {
            return redisSessionTemplate.opsForHash().values(RedisKey.SHIRO_SESSION_CACHE.getKey()).stream().map(o -> (Session) o).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
