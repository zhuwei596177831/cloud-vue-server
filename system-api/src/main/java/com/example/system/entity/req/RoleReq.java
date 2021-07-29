package com.example.system.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2021-06-24 19:38:13
 * @description 角色req
 */
@Getter
@Setter
@ApiModel(value = "角色req")
public class RoleReq implements Serializable {
    private static final long serialVersionUID = -105124591411562583L;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String name;

}
