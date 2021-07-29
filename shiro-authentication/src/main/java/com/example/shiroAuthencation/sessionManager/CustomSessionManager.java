package com.example.shiroAuthencation.sessionManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.owasp.encoder.Encode;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2021-05-28 15:22:50
 * @description
 */
public class CustomSessionManager extends DefaultSessionManager {

    private final Logger logger = LogManager.getLogger(getClass());

    private String sessionIdCookieName;
    private boolean sessionIdUrlRewritingEnabled;

    public CustomSessionManager(String sessionIdCookieName) {
        super();
        this.sessionIdCookieName = sessionIdCookieName;
        this.sessionIdUrlRewritingEnabled = true;
    }

    @Override
    public Serializable getSessionId(SessionKey sessionKey) {
        Serializable id = super.getSessionId(sessionKey);
        if (id == null && WebUtils.isWeb(sessionKey)) {
            ServletRequest request = WebUtils.getRequest(sessionKey);
            ServletResponse response = WebUtils.getResponse(sessionKey);
            id = getSessionId(request, response);
        }
        return id;
    }

    public Serializable getSessionId(ServletRequest request, ServletResponse response) {
        return getReferencedSessionId(request, response);
    }

    private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {

        String id = getSessionIdCookieValue(request, response);
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            //automatically mark it valid here.  If it is invalid, the
            //onUnknownSession method below will be invoked and we'll remove the attribute at that time.
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

            // always set rewrite flag - SHIRO-361
            request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());
        }
        return id;
    }

    private String getSessionIdCookieValue(ServletRequest request, ServletResponse response) {
        if (!(request instanceof HttpServletRequest)) {
            logger.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
            return null;
        }
        return readValue((HttpServletRequest) request, WebUtils.toHttp(response));
    }

    private String readValue(HttpServletRequest request, HttpServletResponse ignored) {
        final String name = this.sessionIdCookieName;
        String value = null;
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            // Validate that the cookie is used at the correct place.
            value = cookie.getValue();
            logger.debug("Found '{}' cookie value [{}]", name, Encode.forHtml(value));
        } else {
            logger.trace("No '{}' cookie value", name);
        }

        return value;
    }

    private Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public String getSessionIdCookieName() {
        return sessionIdCookieName;
    }

    public void setSessionIdCookieName(String sessionIdCookieName) {
        this.sessionIdCookieName = sessionIdCookieName;
    }

    public boolean isSessionIdUrlRewritingEnabled() {
        return sessionIdUrlRewritingEnabled;
    }

    public void setSessionIdUrlRewritingEnabled(boolean sessionIdUrlRewritingEnabled) {
        this.sessionIdUrlRewritingEnabled = sessionIdUrlRewritingEnabled;
    }
}
