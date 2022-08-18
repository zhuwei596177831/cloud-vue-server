package com.example.system;

import com.example.api.system.entity.Menu;
import com.example.api.system.openfeign.client.TestFeign;
import com.example.core.entity.GenericJson;
import com.example.core.entity.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author 朱伟伟
 * @date 2022-08-05 09:52:43
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestFeign testFeign;

    @GetMapping("/feign")
    public GenericJson<String> feign() {
        return testFeign.feign();
    }

    @GetMapping("/time")
    public Json time() {
        Menu menu = new Menu();
        menu.setInputTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        return Json.ok(menu);
    }

}
