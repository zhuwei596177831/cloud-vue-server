package com.example.shiroAuthencation.sessionManager;

import com.example.core.util.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.DelegatingSession;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2021-05-28 15:22:50
 * @description 自定义DefaultSessionManager实现，不使用cookie，使用自定义 token获取session
 */
public class TokenSessionManager extends DefaultSessionManager {

    public TokenSessionManager() {
        super();
    }

    @Override
    protected void onStart(Session session, SessionContext context) {
        //do nothing
    }

    @Override
    public Serializable getSessionId(SessionKey sessionKey) {
        Serializable id = super.getSessionId(sessionKey);
        if (id == null && WebUtils.isHttp(sessionKey)) {
            HttpServletRequest httpRequest = WebUtils.getHttpRequest(sessionKey);
            id = getToken(httpRequest);
        }
        return id;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.X_TOKEN_NAME);
        if (!StringUtils.hasText(token)) {
            token = request.getParameter(Constants.X_TOKEN_NAME);
        }
        if (StringUtils.hasText(token)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            //automatically mark it valid here.  If it is invalid, the
            //onUnknownSession method below will be invoked and we'll remove the attribute at that time.
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }
        return token;
    }

    @Override
    protected Session createExposedSession(Session session, SessionKey key) {
        if (!WebUtils.isWeb(key)) {
            return super.createExposedSession(session, key);
        }

        ServletRequest request = WebUtils.getRequest(key);
        ServletResponse response = WebUtils.getResponse(key);
        SessionKey sessionKey = new WebSessionKey(session.getId(), request, response);
        return new DelegatingSession(this, sessionKey);
    }

    @Override
    protected Session createExposedSession(Session session, SessionContext context) {
        if (!WebUtils.isWeb(context)) {
            return super.createExposedSession(session, context);
        }
        ServletRequest request = WebUtils.getRequest(context);
        ServletResponse response = WebUtils.getResponse(context);
        SessionKey key = new WebSessionKey(session.getId(), request, response);
        return new DelegatingSession(this, key);
    }
}
