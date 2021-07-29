package com.example.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 朱伟伟
 * @date 2021-07-22 17:00:13
 * @description 枚举 model
 */
@Getter
@Setter
@NoArgsConstructor
public class EnumModel extends BaseEntity {
    private static final long serialVersionUID = -5041974911676253351L;

    private Integer value;
    private String desc;

}
