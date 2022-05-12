package com.example.business.openfeign.client;

import com.example.core.entity.Json;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 朱伟伟
 * @date 2021-12-05 16:23:16
 * @description
 */
@FeignClient(
        value = "order",
        path = "/order/openFeign/order",
        contextId = "OrderFeignClient"
)
public interface OrderFeignClient {

    @PostMapping("/saveOrder")
    Json saveOrder();

}
