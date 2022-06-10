package com.example.core.rescode;

import com.example.core.entity.Json;

/**
 * @author 朱伟伟
 * @date 2022-05-07 17:51:04
 * @description 错误码 枚举实现类的父接口
 */
public interface ResponseCode {

    String getCode();

    String getMsg();

    default Json getJson() {
        return new Json(getCode(), getMsg(), false);
    }

}
