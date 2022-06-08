package com.example.core.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 朱伟伟
 * @date 2022-06-07 13:45:03
 * @description 登录日志记录Entity
 */
@Data
@ApiModel(value = "登录日志记录vo")
public class LoginLogVo implements Serializable {

    private static final long serialVersionUID = -3238758757279948028L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 登录账号
     */
    @ApiModelProperty(value = "登录账号")
    private String loginName;

    /**
     * 登录IP地址
     */
    @ApiModelProperty(value = "登录IP地址")
    private String ipAddr;

    /**
     * IP归属地
     */
    @ApiModelProperty(value = "IP归属地")
    private String ipLocation;

    /**
     * 登录状态（0成功 1失败）
     */
    @ApiModelProperty(value = "登录状态（0成功 1失败）")
    private String status;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "访问时间")
    private LocalDateTime accessTime;

}
