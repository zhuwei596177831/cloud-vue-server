package com.example.system.scheduler;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.TaskManagementConfigUtils;

/**
 * @author 朱伟伟
 * @date 2022-09-23 13:58:45
 * @description 自定义spring boot task需要的TaskScheduler
 * </p>配置此类原因：
 * 当EnableWebSocket 开启后，并且没有任何一个WebSocketHandler开启了withSockJS，
 * 则WebSocketConfigurationSupport往容器中放置的是一个NullBean类型的TaskScheduler，
 * 同时@EnableScheduling 开启后，ScheduledAnnotationBeanPostProcessor默认会从容器中查询TaskScheduler类型的bean，
 * 发现存在但是是NullBean，则报错
 * （默认没使用@EnableWebSocket时，生效的是TaskSchedulingAutoConfiguration配置的TaskScheduler）
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(name = TaskManagementConfigUtils.SCHEDULED_ANNOTATION_PROCESSOR_BEAN_NAME)
@EnableConfigurationProperties(TaskExecutionProperties.class)
public class MyTaskSchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler myTaskScheduler(TaskSchedulerBuilder builder) {
        return builder.build();
    }

    @Bean
    public TaskSchedulerBuilder taskSchedulerBuilder(TaskSchedulingProperties properties,
                                                     ObjectProvider<TaskSchedulerCustomizer> taskSchedulerCustomizers) {
        TaskSchedulerBuilder builder = new TaskSchedulerBuilder();
        builder = builder.poolSize(properties.getPool().getSize());
        TaskSchedulingProperties.Shutdown shutdown = properties.getShutdown();
        builder = builder.awaitTermination(shutdown.isAwaitTermination());
        builder = builder.awaitTerminationPeriod(shutdown.getAwaitTerminationPeriod());
        builder = builder.threadNamePrefix(properties.getThreadNamePrefix());
        builder = builder.customizers(taskSchedulerCustomizers);
        return builder;
    }

}
