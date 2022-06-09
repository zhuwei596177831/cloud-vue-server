package com.example.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
 * @description: 角色
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_role", autoResultMap = true)
@ApiModel(value = "角色")
public class Role extends BaseEntity {
    private static final long serialVersionUID = -926347559894265222L;
    /**
     * 角色主键
     */
    @ApiModelProperty(value = "角色主键")
    @NotNull(message = "角色主键不能为空", groups = {Update.class})
    @TableId
    private Long id;

    @NotEmpty(message = "角色编码不能为空", groups = {Add.class, Update.class})
    @ApiModelProperty(value = "角色编码")
    private String code;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @NotEmpty(message = "角色名称不能为空", groups = {Add.class, Update.class})
    private String name;

    /**
     * 录入时间
     */
    @ApiModelProperty(value = "录入时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inputTime;

    /**
     * 录入用户
     */
    @ApiModelProperty(value = "录入用户")
    private Long inputUserId;

    @ApiModelProperty(value = "录入用户名称")
    @TableField(exist = false)
    private String inputUserName;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    /**
     * 修改用户
     */
    @ApiModelProperty(value = "修改用户")
    private Long updateUserId;

    @ApiModelProperty(value = "修改用户名称")
    @TableField(exist = false)
    private String updateUserName;

    @ApiModelProperty(value = "勾选菜单ids")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> checkedMenuIds;

    /**
     * 菜单
     */
    @ApiModelProperty(value = "菜单")
    @TableField(exist = false)
    private List<Menu> menus;

    public interface Add {

    }

    public interface Update {

    }

}

