package com.example.coreweb;

/**
 * @author 朱伟伟
 * @date 2022-09-08 09:57:39
 * @description
 */
public class MyThreadLocalHolder {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void bind(String s) {
        THREAD_LOCAL.set(s);
    }

    public static void unbind() {
        THREAD_LOCAL.remove();
    }

    public static String get() {
        return THREAD_LOCAL.get();
    }
}
