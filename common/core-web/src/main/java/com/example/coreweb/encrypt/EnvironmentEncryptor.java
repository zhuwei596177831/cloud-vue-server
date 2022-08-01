package com.example.coreweb.encrypt;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.example.core.util.Constants;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * @author 朱伟伟
 * @date 2022-08-01 10:43:22
 * @description
 */
public class EnvironmentEncryptor implements TextEncryptor {

    @Override
    public String encrypt(String text) {
        RSA rsa = SecureUtil.rsa(Constants.PWD_RSA_PRIVATE_KEY, Constants.PWD_RSA_PUBLIC_KEY);
        return rsa.encryptBase64(text, KeyType.PublicKey);
    }

    @Override
    public String decrypt(String encryptedText) {
        RSA rsa = SecureUtil.rsa(Constants.PWD_RSA_PRIVATE_KEY, Constants.PWD_RSA_PUBLIC_KEY);
        return rsa.decryptStr(encryptedText, KeyType.PrivateKey);
    }

}
