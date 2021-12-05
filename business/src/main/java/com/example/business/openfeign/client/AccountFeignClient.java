package com.example.business.openfeign.client;

import com.example.business.openfeign.client.fallback.AccountFeignClientFallback;
import com.example.core.entity.Result;
import com.example.core.vo.system.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 朱伟伟
 * @date 2021-11-30 10:49:25
 * @description
 */
@FeignClient(
        value = "account",
        path = "/account/openFeign/account",
        contextId = "AccountFeignClient",
        fallbackFactory = AccountFeignClientFallback.class
)
public interface AccountFeignClient {

    @PostMapping("/saveAccount")
    Result saveAccount(@RequestHeader("header") String header,
                       @RequestParam("name") String name,
                       @RequestBody UserVo userVo);

}
