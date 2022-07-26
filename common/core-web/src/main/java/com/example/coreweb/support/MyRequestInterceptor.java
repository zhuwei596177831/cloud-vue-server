package com.example.coreweb.support;

import com.example.core.util.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.time.Instant;
import java.util.UUID;

/**
 * @author 朱伟伟
 * @date 2021-12-04 21:54:36
 * @description openfeign调用拦截器
 * seata的xid未传递 {@link com.alibaba.cloud.seata.feign.SeataFeignClient}
 */
@Component
public class MyRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        //传递 GatewayTokenCheckFilter 需要的头部信息
        long time = Instant.now().toEpochMilli();
        template.header(Constants.GATEWAY_TIME, String.valueOf(time));
        String nonce = UUID.randomUUID().toString();
        template.header(Constants.GATEWAY_NONCE, nonce);
        String GATEWAY_TOKEN = DigestUtils.md5DigestAsHex((time + Constants.GATEWAY_SIGN_KEY + nonce).getBytes());
        template.header(Constants.GATEWAY_TOKEN, GATEWAY_TOKEN);
        //传递标记参数 内部调用时 绕过shiro认证————CustomAccessFilter
        template.header(Constants.SHIRO_ANON_TIME, String.valueOf(time));
        template.header(Constants.SHIRO_ANON_NONCE, nonce);
        String SHIRO_ANON_TOKEN = DigestUtils.md5DigestAsHex((time + Constants.SHIRO_ANON_SIGN_KEY + nonce).getBytes());
        template.header(Constants.SHIRO_ANON_TOKEN, SHIRO_ANON_TOKEN);
    }
}
