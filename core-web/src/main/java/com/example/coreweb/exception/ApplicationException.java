package com.example.coreweb.exception;

/**
 * @author 朱伟伟
 * @date 2022-05-07 17:54:45
 * @description 应用层异常
 */
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = -1518615560755901748L;

    private ResponseCode responseCode;

    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
