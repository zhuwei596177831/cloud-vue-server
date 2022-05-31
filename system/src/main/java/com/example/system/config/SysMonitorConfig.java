package com.example.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 朱伟伟
 * @date 2022-05-31 11:55:31
 * @description 系统监控新开窗口地址 配置
 */
@Configuration(proxyBeanMethods = false)
@PropertySource(value = {"classpath:sys-monitor-address.properties"})
public class SysMonitorConfig {

    @Value("${open.address.job}")
    private String job;

    @Value("${open.address.nacos}")
    private String nacos;

    @Value("${open.address.monitor}")
    private String monitor;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getNacos() {
        return nacos;
    }

    public void setNacos(String nacos) {
        this.nacos = nacos;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }
}
