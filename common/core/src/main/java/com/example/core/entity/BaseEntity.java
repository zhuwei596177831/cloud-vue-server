package com.example.core.entity;


import com.example.core.util.ObjectMapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

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
        try {
            return ObjectMapperUtil.instance().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
