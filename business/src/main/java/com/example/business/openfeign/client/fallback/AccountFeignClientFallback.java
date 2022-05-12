package com.example.business.openfeign.client.fallback;

import com.example.business.openfeign.client.AccountFeignClient;
import com.example.core.entity.Json;
import com.example.core.vo.system.UserVo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2021-12-01 16:36:28
 * @description
 */
@Component
public class AccountFeignClientFallback implements FallbackFactory<AccountFeignClient> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public AccountFeignClient create(Throwable cause) {
        return new AccountFeignClient() {
            @Override
            public Json saveAccount(String header, String name, UserVo userVo) {
                cause.printStackTrace();
                logger.error("创建账户失败;{}", cause.getMessage());
                return Json.fail(cause.getMessage());
            }
        };
    }
}
