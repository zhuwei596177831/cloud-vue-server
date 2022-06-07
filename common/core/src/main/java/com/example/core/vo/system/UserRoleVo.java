package com.example.core.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 朱伟伟
 * @date 2021-11-28 23:35:55
 * @description 用户角色关联关系 vo
 */
@Getter
@Setter
@NoArgsConstructor
public class UserRoleVo extends BaseVo {
    private static final long serialVersionUID = -590431125891248793L;

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

}
