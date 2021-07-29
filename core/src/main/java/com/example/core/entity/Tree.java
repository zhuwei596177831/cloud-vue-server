package com.example.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author 朱伟伟
 * @date 2021-06-27 19:48:16
 * @description 树
 */
@Getter
@Setter
@NoArgsConstructor
public class Tree extends BaseEntity {
    private static final long serialVersionUID = -6741390166140037788L;
    private Long id;
    private String label;
}
