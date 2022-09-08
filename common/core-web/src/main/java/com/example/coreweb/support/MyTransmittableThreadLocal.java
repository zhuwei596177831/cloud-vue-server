package com.example.coreweb.support;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author 朱伟伟
 * @date 2022-09-08 14:54:45
 * @description
 */
public class MyTransmittableThreadLocal {

    private static final TransmittableThreadLocal<String> TRANSMITTABLE_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void bind(String s) {
        TRANSMITTABLE_THREAD_LOCAL.set(s);
    }

    public static void unbind() {
        TRANSMITTABLE_THREAD_LOCAL.remove();
    }

    public static String get() {
        return TRANSMITTABLE_THREAD_LOCAL.get();
    }

}
