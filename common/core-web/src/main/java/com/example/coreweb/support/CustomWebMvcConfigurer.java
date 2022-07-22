package com.example.coreweb.support;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 朱伟伟
 * @date 2021-02-12 15:19:04
 * @description
 * @see WebMvcConfigurationSupport
 */
@Configuration(proxyBeanMethods = false)
public class CustomWebMvcConfigurer implements WebMvcConfigurer {


    /**
     * 配置HandlerMappings路径匹配选项，如
     * 末尾斜杠匹配
     * 为指定的RequestMapping添加前缀
     * 配置自定义的PathMatcher和UrlPathHelper
     * </p>
     * PathMatcher和UrlPathHelper将作用于：RequestMappingHandlerMapping、ViewControllerMappings、resourceHandlerMapping
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //关闭末尾/匹配方式（开启后 请求路径 /user/ 将无法匹配 /user ）
        configurer.setUseTrailingSlashMatch(false);
        //添加自定义RequestMapping prefix 支持${}占位符方式
//        configurer.addPathPrefix("${scenic.prefix}", ApiScenicPrefix.class::isAssignableFrom);
//        configurer.addPathPrefix(scenicPrefix, HandlerTypePredicate.forBasePackage("com.example.okhttp"));
    }

    /**
     * 配置Spring MVC基于Servlet3.0异步模式实现Servlet异步处理需要的线程池
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); //核心线程数
        executor.setMaxPoolSize(20);  //最大线程数
        executor.setQueueCapacity(1000); //队列大小
        executor.setKeepAliveSeconds(300); //线程最大空闲时间
        executor.setThreadNamePrefix("servlet-async-executor-"); //指定用于新创建的线程名称的前缀。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略（一共四种，此处省略）
        executor.initialize();
        configurer.setTaskExecutor(executor);

    }

    /**
     * 开启default servlet 处理静态资源
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    /**
     * 为FormattingConversionService添加自定义的Converter和Formatter
     * </p>
     * 此处添加的是全局的，永久性的
     * {@link org.springframework.web.bind.annotation.InitBinder} 注解方法方式添加的是局部的，临时性的
     * </p>
     * RequestMappingHandlerAdapter的WebBindingInitializer会设置此FormattingConversionService
     * 最终HandlerMethodArgumentResolver解析请求参数时，WebDataBinder会使用到用户自定义的Converter和Formatter
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    /**
     * 添加handler处理请求的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    /**
     * 自定义映射配置 处理静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(
                        "file:" + "/home/forestallin/uploadPath/",
                        "classpath:/META-INF/resources/",
                        "classpath:/resources/",
                        "classpath:/static/",
                        "classpath:/public/"
                );
    }

    /**
     * 配置HandlerMapping获取Handler时需要的跨域参数
     * <p/>
     * 前后端分离 解决跨域的方式之一
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }

    /**
     * 配置简单的自动控制器，预配置有响应状态代码和/或一个视图来呈现响应主体
     * <p/>
     * 使用SimpleUrlHandlerMapping与Controller接口类型的Handler来处理请求
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    /**
     * 配置自定义的ViewResolver
     * <p/>
     * Spring MVC默认情况下添加了自己的InternalResourceViewResolver
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    /**
     * 添加自定义的HandlerMethodArgumentResolver
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

    }

    /**
     * 添加自定义的HandlerMethodReturnValueHandler
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

    }

    /**
     * 添加自定义的HttpMessageConverter
     * </p>在Spring MVC添加默认的之前执行
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    /**
     * 添加自定义的HttpMessageConverter
     * </p>在Spring MVC添加默认的之后执行
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    /**
     * 添加自定义的HandlerExceptionResolver
     * </p>在Spring MVC添加默认的之前执行
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }

    /**
     * 添加自定义的HandlerExceptionResolver
     * </p>在Spring MVC添加默认的之后执行
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }
}
