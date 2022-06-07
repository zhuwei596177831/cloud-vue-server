package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 朱伟伟
 * @date 2022-06-07 13:45:03
 * @description 登录日志记录Entity
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_login_log")
@ApiModel(value = "登录日志记录")
public class LoginLog extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    @TableId
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

    /**
     * 访问时间 搜索
     */
    @TableField(exist = false)
    private String accessTimeSearchDate;

}
