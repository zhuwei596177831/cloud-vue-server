package com.example.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 朱伟伟
 * @date 2021-05-16 17:07:12
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "分页查询参数")
public class PageInfo extends BaseEntity {
    private static final long serialVersionUID = 7670486731975501401L;
    /**
     * 当前页
     */
    @ApiModelProperty(value = "页码", required = true)
    private int pageNum;
    /**
     * 每页的数量
     */
    @ApiModelProperty(value = "每页数量", required = true)
    private int pageSize;
}
