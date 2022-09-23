package com.example.system.websocket;

import com.example.core.entity.Json;
import com.example.system.applicationevent.JsonApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author 朱伟伟
 * @date 2022-09-23 11:03:41
 * @description
 */
@Component
public class JsonWebSocketScheduler implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void createJson() {
        Json json = Json.ok(UUID.randomUUID().toString());
        applicationEventPublisher.publishEvent(new JsonApplicationEvent(json));
    }

}
