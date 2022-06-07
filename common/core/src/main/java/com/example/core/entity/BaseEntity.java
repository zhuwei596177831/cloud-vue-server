package com.example.core.entity;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2021-01-06 17:57:25
 * @description
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -1546342369485344403L;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
