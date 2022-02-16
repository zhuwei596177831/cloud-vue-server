package com.example.core.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-11-28 23:34:29
 * @description 用户 Vo
 */
@Getter
@Setter
@NoArgsConstructor
public class UserVo extends BaseVo {
    private static final long serialVersionUID = -4956239845042845049L;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "朱伟伟")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", example = "男")
    private String sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "18255181971")
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
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 角色Ids
     */
    @ApiModelProperty(value = "角色Ids")
    private List<Long> roleIds;

    /**
     * 角色名称
     */
    private String roleNames;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    private List<RoleVo> roles;

}