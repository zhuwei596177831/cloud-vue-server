package com.example.system.websocket.scheduler;

import com.example.api.system.entity.Test;
import com.example.coreweb.websocket.WSJson;
import com.example.system.websocket.handler.TestWebSocketHandlerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 朱伟伟
 * @date 2022-09-23 11:03:41
 * @description
 */
//@Component
public class TestWebSocketScheduler {

    @Autowired
    private TestWebSocketHandlerSupport testWebSocketHandler;

    @Scheduled(cron = "0/5 * * * * ?")
    public void createJson() {
        Test test = new Test();
        test.setCreateTime(LocalDateTime.now());
        test.setDate(new Date());
        test.setId(111L);
        test.setName("朱伟伟");
        WSJson wsJson = WSJson.ok("222", test);
        testWebSocketHandler.offer(wsJson);
    }

}
