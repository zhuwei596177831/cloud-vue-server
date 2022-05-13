package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.core.entity.BaseEntity;
import com.example.core.enums.MenuType;
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
 * @date: 2021-06-14 16:36
 * @description: 菜单
 **/
@Getter
@Setter
@NoArgsConstructor
@TableName(value = "t_menu")
@ApiModel(value = "菜单信息")
public class Menu extends BaseEntity {
    private static final long serialVersionUID = 4983128835801319830L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @NotEmpty(message = "主键不能为空", groups = {Update.class})
    private Long id;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码", required = true)
    @NotEmpty(message = "菜单编码不能为空", groups = {Add.class, Update.class})
    private String code;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", required = true)
    @NotEmpty(message = "菜单名称不能为空", groups = {Add.class, Update.class})
    private String name;

    /**
     * 菜单类型 1：模块菜单 2：导航菜单 3：功能按钮
     */
    @ApiModelProperty(value = "菜单类型 1：模块菜单 2：导航菜单 3：功能按钮", required = true)
    @NotNull(message = "菜单类型不能为空", groups = {Add.class, Update.class})
    private Integer type;

    /**
     * 菜单类型名称
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 上级菜单
     */
    @ApiModelProperty(value = "上级菜单")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long parentId;

    @TableField(exist = false)
    private String parentName;

    /**
     * 录入用户
     */
    @ApiModelProperty(value = "录入用户")
    private Long inputUserId;

    /**
     * 录入时间
     */
    @ApiModelProperty(value = "录入时间")
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime inputTime;

    /**
     * 修改用户
     */
    @ApiModelProperty(value = "修改用户")
    private Long updateUserId;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime updateTime;

    /**
     * 菜单路径
     */
    @ApiModelProperty(value = "菜单路径")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String path;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String iconClass;

    @ApiModelProperty(value = "序号")
    private Integer sort;

    /**
     * 子级菜单
     */
    @TableField(exist = false)
    private List<Menu> childMenus;

    public void setType(Integer type) {
        this.type = type;
        this.typeName = MenuType.getDesc(type);
    }

    public interface Add {

    }

    public interface Update {

    }

}

