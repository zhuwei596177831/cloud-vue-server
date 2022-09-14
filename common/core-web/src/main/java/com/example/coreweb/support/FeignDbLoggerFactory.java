package com.example.coreweb.support;

import feign.Logger;
import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 朱伟伟
 * @date 2022-09-13 10:37:26
 * @description
 */
public class FeignDbLoggerFactory implements FeignLoggerFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Logger create(Class<?> type) {
        return new FeignDbLogger(this.applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
