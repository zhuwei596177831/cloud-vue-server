package com.example.coreweb.websocket;

import com.example.core.entity.BaseEntity;
import com.example.core.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 朱伟伟
 * @date 2022-10-09 14:47:55
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WSJson extends BaseEntity {
    private static final long serialVersionUID = 2489624663076121218L;

    private String code;

    private String msg;

    /**
     * 数据标识
     */
    private String flagId;

    private Object data;

    public WSJson(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static WSJson fail(String code, String msg) {
        return new WSJson(code, msg);
    }

    public static WSJson ok(String flagId, Object data) {
        return new WSJson(Constants.SUCCESS_CODE_STRING, Constants.SUCCESS_MSG_STRING, flagId, data);
    }

}
