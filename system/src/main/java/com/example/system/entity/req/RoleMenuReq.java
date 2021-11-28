package com.example.system.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-07-23 17:44:30
 * @description 角色菜单req
 */
@Getter
@Setter
@NoArgsConstructor
public class RoleMenuReq implements Serializable {

    private static final long serialVersionUID = -1269046704056922037L;

    @ApiModelProperty(value = "角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty(value = "菜单列表")
    @NotEmpty(message = "菜单列表不能为空")
    private List<Long> menus;

    @ApiModelProperty(value = "勾选菜单ids")
    @NotEmpty(message = "勾选菜单ids不能为空")
    private List<Long> checkedMenuIds;

}
