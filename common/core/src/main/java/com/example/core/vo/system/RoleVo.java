package com.example.core.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-11-28 23:31:54
 * @description 角色vo
 */
@Getter
@Setter
@NoArgsConstructor
public class RoleVo extends BaseVo {
    private static final long serialVersionUID = 4243122736432276264L;

    /**
     * 角色主键
     */
    @ApiModelProperty(value = "角色主键")
    private Long id;

    @ApiModelProperty(value = "角色编码")
    private String code;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "勾选菜单ids")
    private List<Long> checkedMenuIds;

    /**
     * 菜单
     */
    @ApiModelProperty(value = "菜单")
    private List<MenuVo> menus;

}
