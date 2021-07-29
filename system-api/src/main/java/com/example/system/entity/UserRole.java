package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author: 朱伟伟
 * @date: 2021-06-14 16:41
 * @description: 用户角色关联关系
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_user_role")
@ApiModel(value = "用户角色关联关系")
public class UserRole extends BaseEntity {
    private static final long serialVersionUID = -2518081174239317756L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 录入时间
     */
    @ApiModelProperty(value = "主键")
    private LocalDateTime inputTime;

    /**
     * 录入用户
     */
    @ApiModelProperty(value = "录入用户")
    private Long inputUserId;

}

