package com.example.shiroAuthencation.processor;

import com.example.shiroAuthencation.configuration.ShiroConfiguration;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;

/**
 * @author 朱伟伟
 * @date 2022-07-22 10:43:18
 * @description 配置SpringShiroFilter的DispatcherType
 * dispatcher类型{@code javax.servlet.DispatcherType。在Servlet 3.0中引入了ASYNC}，这意味着在一个请求过程中，
 * 一个过滤器可以在多个线程中被调用
 * </p>
 * Spring MVC异步处理请求时，默认情况下，Filter只拦截一次，再次进入DispatcherServlet的请求不会进入SpringShiroFilter
 * 最终会导致报错（但不影响异步处理请求的结果）
 * 报错原因：FrameworkServlet#publishRequestHandledEvent 在处理请求结束后发布事件时，获取HttpSession报错
 * ——> {@link org.springframework.web.util.WebUtils#getSessionId(HttpServletRequest)}
 * ——> {@link org.apache.shiro.web.servlet.ShiroHttpServletRequest#getSession(boolean)}
 * ——> {@link ShiroHttpServletRequest#getSubject()} (boolean)}
 */
@Component
public class SpringShiroFilterProcessor implements ServletContextAware, ApplicationRunner {

    private static final String[] DEFAULT_URL_MAPPINGS = {"/*"};

    private static final String FILTER_NAME = ShiroConfiguration.SHIRO_FILTER_BEAN_NAME;

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FilterRegistration shiroFilterFilterRegistration = servletContext.getFilterRegistration(FILTER_NAME);
        shiroFilterFilterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC),
                false, DEFAULT_URL_MAPPINGS);
    }
}
