package com.example.core.vo.system;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2022-06-08 09:15:35
 * @description ip地址所属地
 */
@Data
public class IpAddressVo implements Serializable {
    private static final long serialVersionUID = -7757898304793782642L;

    /**
     * 国家
     */
    private String country;

    /**
     * 大区
     */
    private String region;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 网络运营商
     */
    private String isp;

}
