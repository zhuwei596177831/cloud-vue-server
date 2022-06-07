package com.example.core.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-11-28 23:28:01
 * @description 菜单 vo
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuVo extends BaseVo {
    private static final long serialVersionUID = 1724911632782109782L;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码", required = true)
    private String code;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", required = true)
    private String name;

    /**
     * 菜单类型 1：模块菜单 2：导航菜单 3：功能按钮
     */
    @ApiModelProperty(value = "菜单类型 1：模块菜单 2：导航菜单 3：功能按钮", required = true)
    private Integer type;

    /**
     * 菜单类型名称
     */
    private String typeName;

    /**
     * 上级菜单
     */
    @ApiModelProperty(value = "上级菜单")
    private Long parentId;

    private String parentName;

    /**
     * 菜单路径
     */
    @ApiModelProperty(value = "菜单路径")
    private String path;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String iconClass;

    @ApiModelProperty(value = "序号")
    private Integer sort;

    /**
     * 子级菜单
     */
    private List<MenuVo> childMenus;

}
