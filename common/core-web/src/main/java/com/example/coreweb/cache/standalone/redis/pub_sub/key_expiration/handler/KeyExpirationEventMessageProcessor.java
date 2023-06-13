package com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.handler;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * 自定义{@link KeyExpirationEventMessageListener}，基于{@link KeyExpirationHandler}实现
 *
 * @author 朱伟伟
 * @date 2022-03-20 18:35:52
 */
public class KeyExpirationEventMessageProcessor extends KeyExpirationEventMessageListener {

    private static final String keyspaceNotificationsConfigParameter = "Ex";

    private KeyExpirationHandlerComposite keyExpirationHandlerComposite;

    public void setKeyExpirationHandlerComposite(KeyExpirationHandlerComposite keyExpirationHandlerComposite) {
        this.keyExpirationHandlerComposite = keyExpirationHandlerComposite;
    }

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public KeyExpirationEventMessageProcessor(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
        setKeyspaceNotificationsConfigParameter(keyspaceNotificationsConfigParameter);
    }

    @Override
    protected void doHandleMessage(Message message) {
        if (this.keyExpirationHandlerComposite.support(message)) {
            this.keyExpirationHandlerComposite.handle(message);
        }
    }

    @Override
    public void init() {
        if (keyExpirationHandlerComposite == null) {
            throw new IllegalArgumentException("keyExpirationHandlerComposite can not be null");
        }
        super.init();
    }
}
