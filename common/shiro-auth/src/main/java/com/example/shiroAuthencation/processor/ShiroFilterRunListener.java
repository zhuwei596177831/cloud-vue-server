package com.example.shiroAuthencation.processor;

import com.example.shiroAuthencation.configuration.ShiroConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
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
public class ShiroFilterRunListener implements SpringApplicationRunListener {

    private static final String[] DEFAULT_URL_MAPPINGS = {"/*"};

    private static final String FILTER_NAME = ShiroConfiguration.SHIRO_FILTER_BEAN_NAME;

    private final SpringApplication springApplication;
    private final String[] args;

    public ShiroFilterRunListener(SpringApplication springApplication, String[] args) {
        this.springApplication = springApplication;
        this.args = args;
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        if (context instanceof WebApplicationContext) {
            WebApplicationContext webApplicationContext = (WebApplicationContext) context;
            ServletContext servletContext = webApplicationContext.getServletContext();
            Assert.state(servletContext != null, "servletContext can not be null");
            FilterRegistration shiroFilterFilterRegistration = servletContext.getFilterRegistration(FILTER_NAME);
            shiroFilterFilterRegistration.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class),
                    false, DEFAULT_URL_MAPPINGS);
        }
    }

    public SpringApplication getSpringApplication() {
        return springApplication;
    }

    public String[] getArgs() {
        return args;
    }
}
