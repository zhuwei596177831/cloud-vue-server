package com.example.system.websocket.scheduler;

import com.example.api.system.entity.Test;
import com.example.core.entity.Json;
import com.example.system.websocket.handler.TestWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 朱伟伟
 * @date 2022-09-23 11:03:41
 * @description
 */
@Component
public class TestWebSocketScheduler {

    @Autowired
    private TestWebSocketHandler testWebSocketHandler;

    @Scheduled(cron = "0/5 * * * * ?")
    public void createJson() {
        Test test = new Test();
        test.setCreateTime(LocalDateTime.now());
        test.setId(111L);
        test.setName("朱伟伟");
        Json json = Json.ok(test);
        testWebSocketHandler.offer(json);
    }

}
