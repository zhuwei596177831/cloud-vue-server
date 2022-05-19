package com.example.shiroAuthencation.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionContext;

/**
 * @author 朱伟伟
 * @date 2021-05-22 22:19:07
 * @description shiro会话监听器
 */
public class ShiroSessionListener implements SessionListener {

    private final Logger logger = LogManager.getLogger(getClass());

    /**
     * Session创建完成，并使用Response写入Cookie时回调
     * 说明：
     * ShiroRedisCache存储的是SimpleSession，SessionManager#start()方法返回的是DelegatingSession
     *
     * @param session:
     * @author: 朱伟伟
     * @date: 2022-05-17 10:13
     * @see org.apache.shiro.session.mgt.AbstractNativeSessionManager#start(SessionContext)
     **/
    @Override
    public void onStart(Session session) {
        logger.info("会话创建完成：{}", session.getId());
    }

    @Override
    public void onStop(Session session) {
        logger.info("会话停止：{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("会话过期：{}", session.getId());
    }
}
