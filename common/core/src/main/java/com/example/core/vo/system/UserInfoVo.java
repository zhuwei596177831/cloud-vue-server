package com.example.core.vo.system;

import com.example.core.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2022-05-24 09:38:08
 * @description 用户信息 用于前端vuex存储
 */
@Data
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = 6496866600090059285L;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", example = "男")
    private String sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 住址
     */
    @ApiModelProperty(value = "住址")
    private String address;

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名")
    private String loginName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String headImageUrl;

    /**
     * 所属角色
     */
    @ApiModelProperty(value = "所属角色")
    private String roleNames;

    @ApiModelProperty(value = "创建日期")
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime inputTime;

    /**
     * 角色编码 用于前端控制菜单栏
     */
    @ApiModelProperty(value = "角色编码")
    private List<String> roleCodes;

    /**
     * 菜单路径 用于前端控制菜单栏
     */
    @ApiModelProperty(value = "菜单路径")
    private List<String> menuPaths;

    /**
     * 菜单编码 用于前端控制功能按钮
     */
    @ApiModelProperty(value = "菜单编码")
    private List<String> menuCodes;

    /**
     * 系统监控新开窗口地址 job
     */
    @ApiModelProperty(value = "系统监控新开窗口地址 job")
    private String jobAddress;
    /**
     * 系统监控新开窗口地址 nacos
     */
    @ApiModelProperty(value = "系统监控新开窗口地址 nacos")
    private String nacosAddress;
    /**
     * 系统监控新开窗口地址 monitor
     */
    @ApiModelProperty(value = "系统监控新开窗口地址 monitor")
    private String monitorAddress;

}
