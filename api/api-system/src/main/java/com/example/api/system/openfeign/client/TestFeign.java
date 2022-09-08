package com.example.api.system.openfeign.client;

import com.example.core.entity.GenericJson;
import com.example.core.entity.Json;
import com.example.core.util.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 朱伟伟
 * @date 2022-08-05 09:53:55
 * @description
 */
@FeignClient(
        value = Constants.APPLICATION_NAME_APP,
        path = Constants.CONTEXT_PATH_APP + "/test",
        contextId = "TestFeign"
)
public interface TestFeign {

    @PostMapping("/feign")
    GenericJson<String> feign(@RequestParam("name") String name, Json json);

    @GetMapping("/test")
    GenericJson<String> test();

    @GetMapping("/test")
    ResponseEntity<GenericJson<String>> httpEntity();

}
