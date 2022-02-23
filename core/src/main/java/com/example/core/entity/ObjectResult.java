package com.example.core.entity;


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
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "结果")
@SuppressWarnings({"rawtypes", "unchecked"})
public class ObjectResult extends BaseEntity {
    private static final long serialVersionUID = -4845075787435077803L;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "消息")
    private String msg;
    @ApiModelProperty(value = "数据")
    private Object data;

    public ObjectResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ObjectResult ok(Object data) {
        if (data instanceof Page) {
            ObjectResult result = new ObjectResult("0000", "success");
            Page page = (Page) data;
            result.setData(new ObjectPageData(page));
            return result;
        } else if (data instanceof com.baomidou.mybatisplus.extension.plugins.pagination.Page) {
            ObjectResult result = new ObjectResult("0000", "success");
            com.baomidou.mybatisplus.extension.plugins.pagination.Page page = (com.baomidou.mybatisplus.extension.plugins.pagination.Page) data;
            result.setData(new ObjectPageData(page));
            return result;
        } else {
            return new ObjectResult("0000", "success", data);
        }
    }

    public static ObjectResult ok() {
        return new ObjectResult("0000", "success", null);
    }

    public boolean isSuccess() {
        return this.getCode().equals("0000");
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

}
