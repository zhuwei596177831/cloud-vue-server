package com.example.system.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2021-07-25 18:17:18
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
public class UserReq implements Serializable {
    private static final long serialVersionUID = 1954088565555937101L;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "朱伟伟")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", example = "男")
    private String sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "18255181971")
    private String phone;
}
