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
 * @date 2022-06-07 15:02:02
 * @description 操作日志记录Entity
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_ope_log")
@ApiModel(value = "操作日志记录")
public class OpeLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @ApiModelProperty(value = "日志主键")
    @TableId
    private Long id;

    /**
     * 模块标题
     */
    @ApiModelProperty(value = "模块标题")
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;

    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称")
    private String method;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @ApiModelProperty(value = "操作人员")
    private String opeName;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 请求URL
     */
    @ApiModelProperty(value = "请求URL")
    private String opeUrl;

    /**
     * 主机地址
     */
    @ApiModelProperty(value = "主机地址")
    private String opeIp;

    /**
     * 操作地点
     */
    @ApiModelProperty(value = "操作地点")
    private String opeLocation;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String opeParam;

    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    private Integer status;

    /**
     * 错误消息
     */
    @ApiModelProperty(value = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private LocalDateTime opeTime;

    /**
     * 操作时间 搜索
     */
    @TableField(exist = false)
    private String opeTimeSearchDate;

}
