package com.example.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 朱伟伟
 * @date 2021-12-01 16:47:54
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@TableName(value = "t_account_account")
public class Account extends BaseEntity {
    private static final long serialVersionUID = -5830465030399650830L;

    private Long id;

    private String name;

    private String number;

}
