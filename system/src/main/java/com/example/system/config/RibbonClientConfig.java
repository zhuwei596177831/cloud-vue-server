package com.example.system.config;

import com.example.core.util.Constants;
import com.example.system.config.irule.RandomRuleConfig;
import com.example.system.config.irule.RoundRobinRuleConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * @author 朱伟伟
 * @date 2022-08-05 11:12:31
 * @description ribbon负载均衡配置
 * @see org.springframework.cloud.netflix.ribbon.SpringClientFactory
 * @see org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient
 * @see org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory
 */
@RibbonClients(
        {
                @RibbonClient(name = Constants.APPLICATION_NAME_APP, configuration = RandomRuleConfig.class),
                @RibbonClient(name = Constants.APPLICATION_NAME_FILE, configuration = RoundRobinRuleConfig.class)
        }
)
public class RibbonClientConfig {

}
