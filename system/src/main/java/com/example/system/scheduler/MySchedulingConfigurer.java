package com.example.system.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author 朱伟伟
 * @date 2022-09-23 11:27:13
 * @description
 */
@EnableScheduling
@Configuration(proxyBeanMethods = false)
public class MySchedulingConfigurer implements SchedulingConfigurer {

    @Autowired
    @Qualifier("myTaskScheduler")
    private TaskScheduler myTaskScheduler;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(myTaskScheduler);
    }

}
