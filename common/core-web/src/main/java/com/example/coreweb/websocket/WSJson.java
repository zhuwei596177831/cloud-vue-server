package com.example.coreweb.websocket;

import com.example.core.entity.BaseEntity;
import com.example.core.util.Constants;

/**
 * @author 朱伟伟
 * @date 2022-10-09 14:47:55
 * @description
 */
public class WSJson extends BaseEntity {
    private static final long serialVersionUID = 2489624663076121218L;

    private String code;

    private String msg;

    /**
     * 数据标识
     */
    private String flagId;

    private Object data;

    private WSJson(String code, String msg, String flagId, Object data) {
        this.code = code;
        this.msg = msg;
        this.flagId = flagId;
        this.data = data;
    }

    public static WSJson ok(String flagId, Object data) {
        return new WSJson(Constants.SUCCESS_CODE_STRING, Constants.SUCCESS_MSG_STRING, flagId, data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFlagId() {
        return flagId;
    }

    public void setFlagId(String flagId) {
        this.flagId = flagId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
