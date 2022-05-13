package com.example.shiroAuthencation.configuration;

import com.example.core.enums.GatewayTokenCheckWhiteUrl;
import com.example.core.util.Constants;
import com.example.shiroAuthencation.filter.CustomAccessFilter;
import com.example.shiroAuthencation.listener.ShiroSessionListener;
import com.example.shiroAuthencation.realm.UserNamePasswordRealm;
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
     * 配置自定义cache 实现session持久化
     *
     * @author: 朱伟伟
     * @date: 2022-05-13 15:33
     **/
    @Bean
    public ShiroReisCache shiroReisCache() {
        return new ShiroReisCache();
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
     * 配置自定义EnterpriseCacheSessionDAO 实现session持久化
     *
     * @param shiroReisCacheManager:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:34
     **/
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

    /**
     * 配置session相关的DefaultWebSessionManager
     *
     * @param enterpriseCacheSessionDAO:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:18
     * @see org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler
     **/
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(EnterpriseCacheSessionDAO enterpriseCacheSessionDAO) {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        SimpleCookie shiroSessionIdCookie = new SimpleCookie(Constants.SHIRO_COOKIE_NAME);
        //各个模块共享cookie
        shiroSessionIdCookie.setPath("/");
        defaultWebSessionManager.setSessionIdCookie(shiroSessionIdCookie);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        //每3分钟进行一次Session是否失效的检测
        defaultWebSessionManager.setSessionValidationInterval(3 * 60 * 1000);
        defaultWebSessionManager.setSessionListeners(Collections.singleton(new ShiroSessionListener()));
        //session有效期为30分钟
        defaultWebSessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        defaultWebSessionManager.setSessionDAO(enterpriseCacheSessionDAO);
        return defaultWebSessionManager;
    }

    /**
     * 配置认证相关的DefaultWebSecurityManager
     *
     * @param sessionManager:
     * @param userNamePasswordRealm:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:19
     **/
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(SessionManager sessionManager, UserNamePasswordRealm userNamePasswordRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager);
        List<Realm> realms = new ArrayList<>();
        realms.add(userNamePasswordRealm);
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
    @Bean
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
        addGatewayUrlWhiteList(filterChainDefinitionMap);
        filterChainDefinitionMap.put("/login", DefaultFilter.anon.name());
        filterChainDefinitionMap.put("/**", CustomAccessFilter.class.getSimpleName());
    }

    /**
     * 网关白名单配置的url统一直接放行，不做拦截
     *
     * @param filterChainDefinitionMap:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:29
     **/
    private void addGatewayUrlWhiteList(Map<String, String> filterChainDefinitionMap) {
        for (GatewayTokenCheckWhiteUrl gatewayTokenCheckWhiteUrl : GatewayTokenCheckWhiteUrl.values()) {
            filterChainDefinitionMap.put(gatewayTokenCheckWhiteUrl.getUrl(), DefaultFilter.anon.name());
        }
    }

    /**
     * 添加LifecycleBeanPostProcessor配置项，LifecycleBeanPostProcessor将统一管理Shiro、Initializable和Destroyable的实现类，
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
