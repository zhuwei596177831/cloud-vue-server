package com.example.shiroAuthencation.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @author 朱伟伟
 * @date 2021-05-22 22:19:07
 * @description shiro会话监听器
 */
public class ShiroSessionListener implements SessionListener {

    private final Logger logger = LogManager.getLogger(getClass());

    @Override
    public void onStart(Session session) {
        logger.info("会话创建：{}", session.getId());
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
