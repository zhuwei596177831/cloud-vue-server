package com.example.coreweb.support;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;

/**
 * @author 朱伟伟
 * @date 2022-09-07 17:51:49
 * @description
 */
public class DelegateCallable<V> implements Callable<V> {

    private final RequestAttributes delegate;
    private final Callable<V> callable;

    public DelegateCallable(Callable<V> callable) {
        this.delegate = RequestContextHolder.getRequestAttributes();
        this.callable = callable;
    }


    @Override
    public V call() throws Exception {
        try {
            RequestContextHolder.setRequestAttributes(this.delegate);
            return callable.call();
        } finally {
            RequestContextHolder.resetRequestAttributes();
        }
    }
}
