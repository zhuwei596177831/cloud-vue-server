package com.example.core.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author 朱伟伟
 * @date 2021-05-10 17:06:09
 * @description shiro用户 密码加密
 */
public class PasswordUtils {

    /**
     * md5加密，方式：md5(用户名+密码)
     *
     * @param username:
     * @param password:
     * @author: 朱伟伟
     * @date: 2022-05-13 15:53
     **/
    public static String md5(String username, String password) {
        return new SimpleHash(Md5Hash.ALGORITHM_NAME, password, username).toHex();
    }

    public static void main(String[] args) {
        System.out.println(md5("admin", Constants.DEFAULT_PASSWORD));
    }

}
