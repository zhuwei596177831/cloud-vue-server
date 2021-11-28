package com.example.core.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 朱伟伟
 * @date 2021-11-28 23:33:32
 * @description 角色菜单关联关系 vo
 */
@Getter
@Setter
@NoArgsConstructor
public class RoleMenuVo extends BaseVo {
    private static final long serialVersionUID = -5772001984711984263L;

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

}
