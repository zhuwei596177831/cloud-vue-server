package com.example.system.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 朱伟伟
 * @date 2020-12-07 09:43:27
 * @description 自定义 异步任务的线程池
 * @see org.springframework.scheduling.annotation.Async
 * @see org.springframework.aop.interceptor.AsyncExecutionInterceptor
 */
@Component
public class SystemAsyncConfigurer implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); //核心线程数
        executor.setMaxPoolSize(20);  //最大线程数
        executor.setQueueCapacity(1000); //队列大小
        executor.setKeepAliveSeconds(300); //线程最大空闲时间
        executor.setThreadNamePrefix("async-executor-system-"); //指定用于新创建的线程名称的前缀。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略（一共四种，此处省略）
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
