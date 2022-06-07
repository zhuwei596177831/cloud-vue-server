package com.example.core.util;

import cn.hutool.crypto.SecureUtil;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author 朱伟伟
 * @date 2022-06-06 17:39:20
 * @description
 */
public class HutoolUtils {
    public static void main(String[] args) {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        PublicKey aPublic = pair.getPublic();
        PrivateKey aPrivate = pair.getPrivate();
        System.out.println(new String(Base64.getEncoder().encode(aPublic.getEncoded())));
        System.out.println("=========");
        System.out.println(new String(Base64.getEncoder().encode(aPrivate.getEncoded())));
    }
}
