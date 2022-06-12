package com.example.core.entity;


import com.example.core.util.Constants;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 朱伟伟
 * @date 2020-12-27 11:52:23
 * @description 通用的返回结果
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "结果")
@SuppressWarnings({"rawtypes", "unchecked"})
public class Json extends BaseEntity {
    private static final long serialVersionUID = -4845075787435077803L;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "消息")
    private String msg;
    @ApiModelProperty(value = "成功/失败标志")
    private boolean success;
    @ApiModelProperty(value = "数据")
    private Object data;

    public Json(String code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public Json(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public static Json ok(Object data) {
        if (data instanceof Page) {
            return new Json(Constants.SUCCESS_CODE_STRING, Constants.SUCCESS_MSG_STRING, true, new PageData((Page) data));
        } else if (data instanceof com.baomidou.mybatisplus.extension.plugins.pagination.Page) {
            Json result = new Json(Constants.SUCCESS_CODE_STRING, Constants.SUCCESS_MSG_STRING, true);
            com.baomidou.mybatisplus.extension.plugins.pagination.Page page = (com.baomidou.mybatisplus.extension.plugins.pagination.Page) data;
            result.setData(new PageData(page));
            return result;
        } else {
            return new Json(Constants.SUCCESS_CODE_STRING, Constants.SUCCESS_MSG_STRING, true, data);
        }
    }

    public static Json fail(String code, String msg) {
        return new Json(code, msg, false);
    }

    public static Json fail(String msg) {
        return new Json(msg, false);
    }

    public static Json success() {
        return new Json(Constants.SUCCESS_MSG_STRING, true);
    }

    public static Json success(String msg) {
        return new Json(msg, true);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean notSuccess() {
        return !isSuccess();
    }

}
