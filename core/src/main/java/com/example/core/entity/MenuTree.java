package com.example.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-06-27 19:48:16
 * @description 菜单树
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuTree extends Tree {

    private static final long serialVersionUID = 875656619425114034L;
    /**
     * 菜单类型
     */
    private Integer type;

    private List<MenuTree> children;
}
