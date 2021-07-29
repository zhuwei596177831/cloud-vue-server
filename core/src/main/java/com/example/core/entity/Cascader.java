package com.example.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-07-22 17:38:15
 * @description 级联选择model
 */
@Getter
@Setter
@NoArgsConstructor
public class Cascader extends BaseEntity {
    private static final long serialVersionUID = 7836065476651480506L;
    private String label;
    private String value;
    @JsonIgnore
    private String parentValue;
    private List<Cascader> children;
}
