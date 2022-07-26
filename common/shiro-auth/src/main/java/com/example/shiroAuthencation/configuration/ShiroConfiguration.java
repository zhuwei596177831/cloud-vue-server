package com.example.shiroAuthencation.configuration;

import com.example.shiroAuthencation.enums.ShiroWhiteListUrl;
import com.example.shiroAuthencation.filter.CustomAccessFilter;
import com.example.shiroAuthencation.listener.ShiroSessionListener;
import com.example.shiroAuthencation.realm.UserNamePasswordRealm;
import com.example.shiroAuthencation.sessionManager.TokenSessionManager;
import com.example.shiroAuthencation.sessioncache.RedisSessionTemplate;
import com.example.shiroAuthencation.sessioncache.ShiroReisCache;
import com.example.shiroAuthencation.sessioncache.ShiroReisCacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-05-19 09:53:57
 * @description shiro配置
 * @see org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration
 * @see org.apache.shiro.spring.boot.autoconfigure.ShiroBeanAutoConfiguration
 * @see org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration
 */
@Configuration
public class ShiroConfiguration {

    public static final String SHIRO_FILTER_BEAN_NAME = "shiroFilterFactoryBean";

    /**
     * 配置登录时的认证Realm
     *
     * @author: 朱伟伟
     * @date: 2022-05-13 15:30
     **/
    @Bean
    public UserNamePasswordRealm userNamePasswordRealm() {
        UserNamePasswordRealm userNamePasswordRealm = new UserNamePasswordRealm();
        userNamePasswordRealm.setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));
        return userNamePasswordRealm;
    }

    /**
     * 配置RedisSessionTemplate实现Session的持久化
     *
     * @param redisConnectionFactory:
     * @author: 朱伟伟
     * @date: 2022-05-16 16:14
     **/
    @Bean
    public RedisSessionTemplate redisSessionTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new RedisSessionTemplate(redisConnectionFactory);
    }

    /**
     * 配置自定义cache 实现session持久化
     *
     * @author: 朱伟伟
     * @date: 2022-05-13 15:33
     **/
    @Bean
    public ShiroReisCache shiroReisCache(RedisSessionTemplate redisSessionTemplate) {
        return new ShiroReisCache(redisSessionTemplate);
    }

    /**
     * 配置自定义CacheManager 实现session持久化
     *
     * @param shiroReisCache:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:34
     **/
    @Bean
    public ShiroReisCacheManager shiroReisCacheManager(ShiroReisCache shiroReisCache) {
        return new ShiroReisCacheManager(shiroReisCache);
    }

    /**
     * 配置自定义的SessionManager（基于自定义的token机制）
     *
     * @author: 朱伟伟
     * @date: 2022-05-16 16:50
     **/
    @Bean
    public TokenSessionManager customSessionManager(CacheManager cacheManager) {
        TokenSessionManager tokenSessionManager = new TokenSessionManager();
        tokenSessionManager.setSessionValidationSchedulerEnabled(true);
        //设置每10分钟进行一次Session是否失效的检测（默认60分钟一次）
        tokenSessionManager.setSessionValidationInterval(10 * 60 * 1000);
        //设置session有效期为30分钟（默认也是30分钟）
        tokenSessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        //设置持久化Session的SessionDAO
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setCacheManager(cacheManager);
        enterpriseCacheSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        tokenSessionManager.setSessionDAO(enterpriseCacheSessionDAO);
        tokenSessionManager.setSessionListeners(Collections.singleton(new ShiroSessionListener()));
        return tokenSessionManager;
    }

    /**
     * 配置支持Session分布式缓存功能的DefaultWebSessionManager（基于默认的cookie-session机制）
     *
     * @author: 朱伟伟
     * @date: 2022-05-13 15:18
     * Session是否失效的检测:
     * @see org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler
     * 由Servlet容器管理HttpSession的SessionManager:
     * @see org.apache.shiro.web.session.mgt.ServletContainerSessionManager
     **/
    //@Bean
    //public DefaultWebSessionManager defaultWebSessionManager(CacheManager cacheManager) {
    //    DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
    //    SimpleCookie shiroSessionIdCookie = new SimpleCookie(Constants.SHIRO_COOKIE_NAME);
    //    //1、手动设置Path使各个模块共享cookie，请求别的模块的接口时，cookie会自动带上
    //    //如果配置了contextPath后，则Path取contextPath，没有配置，则是/，默认情况下，浏览器关闭，cookie就失效
    //    shiroSessionIdCookie.setPath("/");
    //    defaultWebSessionManager.setSessionIdCookie(shiroSessionIdCookie);
    //    defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
    //    //2、设置每10分钟进行一次Session是否失效的检测（默认60分钟一次）
    //    defaultWebSessionManager.setSessionValidationInterval(10 * 60 * 1000);
    //    defaultWebSessionManager.setSessionListeners(Collections.singleton(new ShiroSessionListener()));
    //    //3、设置session有效期为30分钟（默认也是30分钟）
    //    defaultWebSessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
    //    //4、设置持久化Session的SessionDAO
    //    EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
    //    enterpriseCacheSessionDAO.setCacheManager(cacheManager);
    //    defaultWebSessionManager.setSessionDAO(enterpriseCacheSessionDAO);
    //    return defaultWebSessionManager;
    //}

    /**
     * 配置认证相关的DefaultWebSecurityManager
     *
     * @param sessionManager:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:19
     **/
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(SessionManager sessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager);
        //使用TokenSessionManager时不需要RememberMeCookie
        defaultWebSecurityManager.setRememberMeManager(null);
        List<Realm> realms = new ArrayList<>();
        realms.add(userNamePasswordRealm());
        defaultWebSecurityManager.setRealms(realms);
        return defaultWebSecurityManager;
    }

    /**
     * 以FactoryBean的方式配置shiro的SpringShiroFilter
     * 其他的根据业务需要实现的自定义的Filter都由此类代理调用其doFilter方法
     *
     * @param defaultWebSecurityManager:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:22
     **/
    @Bean(name = SHIRO_FILTER_BEAN_NAME)
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        loadFilters(shiroFilterFactoryBean);
        loadFilterChainDefinitionMap(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置所有自定义的filter
     *
     * @param shiroFilterFactoryBean:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:27
     **/
    private void loadFilters(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put(CustomAccessFilter.class.getSimpleName(), new CustomAccessFilter());
    }

    /**
     * 配置请求路径和Filter的对应关系
     *
     * @param shiroFilterFactoryBean:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:28
     **/
    private void loadFilterChainDefinitionMap(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        addWhiteListUrl(filterChainDefinitionMap);
        filterChainDefinitionMap.put("/**", CustomAccessFilter.class.getSimpleName());
    }

    /**
     * 白名单配置的url统一直接放行，不做拦截
     *
     * @param filterChainDefinitionMap:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:29
     **/
    private void addWhiteListUrl(Map<String, String> filterChainDefinitionMap) {
        for (ShiroWhiteListUrl shiroWhiteListUrl : ShiroWhiteListUrl.values()) {
            filterChainDefinitionMap.put(shiroWhiteListUrl.getUrl(), DefaultFilter.anon.name());
        }
    }

    /**
     * 配置实现shiro注解权限需要的PointcutAdvisor
     *
     * @param securityManager:
     * @author: 朱伟伟
     * @date: 2022-05-20 16:37
     * @see org.apache.shiro.authz.annotation.RequiresPermissions
     * @see org.apache.shiro.authz.annotation.RequiresRoles
     * @see org.apache.shiro.authz.annotation.RequiresAuthentication
     * @see org.apache.shiro.authz.annotation.RequiresGuest
     * @see org.apache.shiro.authz.annotation.RequiresUser
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
