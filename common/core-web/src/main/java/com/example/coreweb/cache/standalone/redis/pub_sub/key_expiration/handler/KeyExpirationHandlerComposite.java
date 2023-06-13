package com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.handler;

import org.springframework.data.redis.connection.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将实际的处理逻辑委托给内部的keyExpirationHandlers，缓存了Message对应的KeyExpirationHandler，方便更快的查找
 *
 * @author 朱伟伟
 * @date 2023-06-13 09:54:00
 */
public class KeyExpirationHandlerComposite implements KeyExpirationHandler {

    private final Map<String, KeyExpirationHandler> handlerCache = new ConcurrentHashMap<>(128);

    private final List<KeyExpirationHandler> keyExpirationHandlers = new ArrayList<>();

    public KeyExpirationHandlerComposite() {
    }

    public KeyExpirationHandlerComposite(KeyExpirationHandler... keyExpirationHandlers) {
        setKeyExpirationHandlers(Arrays.asList(keyExpirationHandlers));
    }

    public void setKeyExpirationHandlers(List<KeyExpirationHandler> keyExpirationHandlers) {
        this.keyExpirationHandlers.addAll(keyExpirationHandlers);
    }

    @Override
    public boolean support(Message message) {
        return getExpirationHandler(message) != null;
    }

    @Override
    public void handle(Message message) {
        KeyExpirationHandler expirationHandler = getExpirationHandler(message);
        if (expirationHandler == null) {
            throw new IllegalArgumentException("Unsupported message [" + new String(message.getBody()) + "]. support method should be called first.");
        }
        expirationHandler.handle(message);
    }

    private KeyExpirationHandler getExpirationHandler(Message message) {
        String key = new String(message.getBody());
        KeyExpirationHandler result = this.handlerCache.get(key);
        if (result == null) {
            for (KeyExpirationHandler keyExpirationHandler : keyExpirationHandlers) {
                if (keyExpirationHandler.support(message)) {
                    result = keyExpirationHandler;
                    this.handlerCache.put(key, keyExpirationHandler);
                    break;
                }
            }
        }
        return result;
    }

}
