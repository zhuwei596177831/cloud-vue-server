package com.example.shiroAuthencation.configuration;

import com.example.core.enums.GatewayWhiteUrl;
import com.example.core.util.ConstantsHolder;
import com.example.shiroAuthencation.filter.CustomAccessFilter;
import com.example.shiroAuthencation.listener.ShiroSessionListener;
import com.example.shiroAuthencation.realm.UserNamePasswordRealm;
import com.example.shiroAuthencation.sessionManager.CustomSessionManager;
import com.example.shiroAuthencation.sessioncache.ShiroReisCache;
import com.example.shiroAuthencation.sessioncache.ShiroReisCacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-05-19 09:53:57
 * @description shiro配置
 */
@Configuration(proxyBeanMethods = false)
public class ShiroConfiguration {


    @Bean
    public UserNamePasswordRealm userNamePasswordRealm() {
        UserNamePasswordRealm userNamePasswordRealm = new UserNamePasswordRealm();
        userNamePasswordRealm.setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));
        return userNamePasswordRealm;
    }


    @Bean
    public ShiroReisCache shiroReisCache() {
        return new ShiroReisCache();
    }

    @Bean
    public ShiroReisCacheManager shiroReisCacheManager(ShiroReisCache shiroReisCache) {
        return new ShiroReisCacheManager(shiroReisCache);
    }

    @Bean
    public EnterpriseCacheSessionDAO enterpriseCacheSessionDAO(ShiroReisCacheManager shiroReisCacheManager) {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setCacheManager(shiroReisCacheManager);
        return enterpriseCacheSessionDAO;
    }

//    @Bean
//    public CustomSessionManager customSessionManager(EnterpriseCacheSessionDAO enterpriseCacheSessionDAO) {
//        CustomSessionManager customSessionManager = new CustomSessionManager(ConstantsHolder.SHIRO_COOKIE_NAME);
//        customSessionManager.setSessionValidationSchedulerEnabled(true);
//        //3 minutes
//        final long sessionValidationInterval = 3 * 60 * 1000;
//        customSessionManager.setSessionValidationInterval(sessionValidationInterval);
//        customSessionManager.setSessionListeners(Collections.singleton(new ShiroSessionListener()));
//        customSessionManager.setGlobalSessionTimeout(30 * 60 * 1000);//30分钟
//        customSessionManager.setSessionDAO(enterpriseCacheSessionDAO);
//        return customSessionManager;
//    }

    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(EnterpriseCacheSessionDAO enterpriseCacheSessionDAO) {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        SimpleCookie shiroSessionIdCookie = new SimpleCookie(ConstantsHolder.SHIRO_COOKIE_NAME);
        //各个模块共享cookie
        shiroSessionIdCookie.setPath("/");
        defaultWebSessionManager.setSessionIdCookie(shiroSessionIdCookie);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        //3分钟
        final long sessionValidationInterval = 3 * 60 * 1000;
        defaultWebSessionManager.setSessionValidationInterval(sessionValidationInterval);
        defaultWebSessionManager.setSessionListeners(Collections.singleton(new ShiroSessionListener()));
        //30分钟
        defaultWebSessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        defaultWebSessionManager.setSessionDAO(enterpriseCacheSessionDAO);
        return defaultWebSessionManager;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(SessionManager sessionManager, UserNamePasswordRealm userNamePasswordRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager);
        List<Realm> realms = new ArrayList<>();
        realms.add(userNamePasswordRealm);
        defaultWebSecurityManager.setRealms(realms);
        return defaultWebSecurityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        loadFilters(shiroFilterFactoryBean);
        loadFilterChainDefinitionMap(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }


    private void loadFilters(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put(CustomAccessFilter.class.getSimpleName(), new CustomAccessFilter());
    }

    private void loadFilterChainDefinitionMap(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        addGatewayUrlWhiteList(filterChainDefinitionMap);
        filterChainDefinitionMap.put("/login", DefaultFilter.anon.name());
        filterChainDefinitionMap.put("/**", CustomAccessFilter.class.getSimpleName());
    }

    private void addGatewayUrlWhiteList(Map<String, String> filterChainDefinitionMap) {
        for (GatewayWhiteUrl gatewayWhiteUrl : GatewayWhiteUrl.values()) {
            filterChainDefinitionMap.put(gatewayWhiteUrl.getUrl(), DefaultFilter.anon.name());
        }
    }

    /**
     * 添加LifecycleBeanPostProcessor配置项，LifecycleBeanPostProcessor将统一管理Initializable和Destroyable的实现类，
     * 从而达到统一管理Shiro Bean的生命周期的目的
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * DefaultAdvisorAutoProxyCreator的作用是动态代理Shiro的事务，最终将事务交由Spring进行统一管理。
     * 此配置项需要依赖于LifecycleBeanPostProcessor
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
