package com.example.business.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 朱伟伟
 * @date 2021-11-30 10:49:25
 * @description
 */
@FeignClient(
        value = "order",
        path = "/rpc/order",
        contextId = "TestFeignClient"
)
public interface TestFeignClient {



}
