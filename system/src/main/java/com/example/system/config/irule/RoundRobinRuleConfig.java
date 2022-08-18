package com.example.system.config.irule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;

/**
 * @author 朱伟伟
 * @date 2022-08-05 11:21:54
 * @description
 */
public class RoundRobinRuleConfig {

    @Bean
    public IRule roundRobinRule() {
        return new RoundRobinRule();
    }

}
