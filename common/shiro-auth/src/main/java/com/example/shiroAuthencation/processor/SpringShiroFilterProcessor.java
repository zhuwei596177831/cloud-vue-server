package com.example.shiroAuthencation.processor;

import com.example.shiroAuthencation.configuration.ShiroConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;

/**
 * @author 朱伟伟
 * @date 2022-07-22 10:43:18
 * @see ShiroFilterRunListener
 */
@Deprecated
//@Component
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
        shiroFilterFilterRegistration.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class),
                false, DEFAULT_URL_MAPPINGS);
    }
}
