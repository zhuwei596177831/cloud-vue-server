package com.example.system.config.irule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;

/**
 * @author 朱伟伟
 * @date 2022-08-05 11:17:16
 * @description
 */
public class RandomRuleConfig {

    @Bean
    public IRule randomRule() {
        return new RandomRule();
    }

}
