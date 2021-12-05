package com.example.order.openfeign.client;

import com.example.core.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 朱伟伟
 * @date 2021-12-05 16:23:16
 * @description
 */
@FeignClient(
        value = "storage",
        path = "/storage/openFeign/storage",
        contextId = "StorageFeignClient"
)
public interface StorageFeignClient {

    @PostMapping("/saveStorage")
    Result saveStorage();

}
