package com.example.core.vo.system;

import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2022-05-25 16:50:19
 * @description
 */
public class TokenVo implements Serializable {
    private static final long serialVersionUID = 5441108533229895161L;

    /**
     * 生成的token
     */
    private Serializable token;

    public Serializable getToken() {
        return token;
    }

    public void setToken(Serializable token) {
        this.token = token;
    }
}
