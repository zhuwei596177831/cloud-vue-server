package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author: 朱伟伟
 * @date: 2021-06-14 16:44
 * @description: 角色菜单关联关系
 **/
@Getter
@Setter
@NoArgsConstructor
@TableName(value = "t_role_menu")
@ApiModel(value = "角色菜单关联关系")
public class RoleMenu extends BaseEntity {
    private static final long serialVersionUID = 1476268484180090896L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId
    private Long id;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private Long menuId;

    /**
     * 录入用户
     */
    @ApiModelProperty(value = "录入用户")
    private Long inputUserId;

    /**
     * 录入时间
     */
    @ApiModelProperty(value = "录入时间")
    private LocalDateTime inputTime;

}

