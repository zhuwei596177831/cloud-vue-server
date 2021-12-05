package com.example.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 朱伟伟
 * @date 2021-12-01 17:31:00
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@TableName(value = "t_business_test")
public class Test extends BaseEntity {

    private static final long serialVersionUID = 8576538059701250606L;

    private Long id;

    private String name;
}
