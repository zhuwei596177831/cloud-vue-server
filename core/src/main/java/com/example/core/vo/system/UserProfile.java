package com.example.core.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2022-06-06 11:04:57
 * @description 个人中心 用户资料
 */
@Data
public class UserProfile implements Serializable {
    private static final long serialVersionUID = -6575748608679519624L;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "朱伟伟")
    @NotEmpty(message = "姓名不能为空", groups = {UpdateProfile.class})
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", example = "男")
    @NotEmpty(message = "性别不能为空", groups = {UpdateProfile.class})
    private String sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "18255181971")
    @NotEmpty(message = "手机号不能为空", groups = {UpdateProfile.class})
    @Pattern(message = "手机号格式不正确", regexp = "/^1[3|4|5|6|7|8|9][0-9]\\d{8}$/", groups = {UpdateProfile.class})
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确", groups = {UpdateProfile.class})
    private String email;

    /**
     * 住址
     */
    @ApiModelProperty(value = "住址")
    private String address;

    /**
     * 旧密码
     */
    @ApiModelProperty(value = "旧密码")
    @NotEmpty(message = "旧密码不能为空", groups = {UpdatePwd.class})
    private String oldPassword;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码")
    @NotEmpty(message = "新密码不能为空", groups = {UpdatePwd.class})
    private String newPassword;

    /**
     * 确认密码
     */
    @ApiModelProperty(value = "确认密码")
    @NotEmpty(message = "确认密码不能为空", groups = {UpdatePwd.class})
    private String confirmPassword;

    public interface UpdateProfile {

    }

    public interface UpdatePwd {

    }

}
