package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.core.entity.BaseEntity;
import com.example.core.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: 朱伟伟
 * @date: 2021-05-19 09:41
 * @description: 用户
 **/
@NoArgsConstructor
@Getter
@Setter
@TableName(value = "t_user")
@ApiModel(value = "用户信息")
public class User extends BaseEntity {
    private static final long serialVersionUID = -4212581425475592954L;
    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键")
    @NotNull(message = "用户主键不能为空", groups = {Update.class})
    @TableId
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "朱伟伟")
    @NotEmpty(message = "姓名不能为空", groups = {Add.class, Update.class})
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", example = "男")
    @NotEmpty(message = "性别不能为空", groups = {Add.class, Update.class})
    private String sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "18255181971")
    @NotEmpty(message = "手机号不能为空", groups = {Add.class, Update.class})
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
    @NotEmpty(message = "登录名不能为空", groups = {Add.class, Update.class})
    private String loginName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 录入时间
     */
    @ApiModelProperty(value = "录入时间")
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime inputTime;

    /**
     * 录入用户ID
     */
    @ApiModelProperty(value = "录入用户")
    private Long inputUserId;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime updateTime;

    /**
     * 修改用户ID
     */
    @ApiModelProperty(value = "修改用户")
    private Long updateUserId;

    /**
     * 角色Ids
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "角色Ids")
    @NotEmpty(message = "角色Ids不能为空", groups = {Add.class, Update.class})
    private List<Long> roleIds;

    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleNames;

    /**
     * 角色
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "角色")
    private List<Role> roles;

    public interface Add {

    }

    public interface Update {

    }

}

