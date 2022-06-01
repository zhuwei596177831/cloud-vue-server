package com.example.core.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2022-06-01 15:18:36
 * @description 前端el-select下拉框 vo类
 */
@Data
public class ElSelectVo implements Serializable {
    private static final long serialVersionUID = -3356252845754797789L;
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

}
