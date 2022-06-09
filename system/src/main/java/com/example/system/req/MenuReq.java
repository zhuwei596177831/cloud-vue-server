package com.example.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-06-24 19:38:13
 * @description 菜单req
 */
@Getter
@Setter
@ApiModel(value = "菜单req")
public class MenuReq implements Serializable {
    private static final long serialVersionUID = -105124591411562583L;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 菜单类型 1：模块菜单 2：导航菜单 3：功能按钮
     */
    @ApiModelProperty(value = "菜单类型 1：模块菜单 2：导航菜单 3：功能按钮")
    private Integer type;

    @ApiModelProperty(value = "菜单类型 1：模块菜单 2：导航菜单 3：功能按钮")
    private List<Integer> types;

    @ApiModelProperty(value = "上级菜单id")
    private Long parentId;
}
