package com.example.core.threadpool;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 朱伟伟
 * @date 2022-03-01 21:43:08
 * @description 自定义线程池
 */
public abstract class MyExecutor {

    private static final ExecutorService delegate =
            new ThreadPoolExecutor(10, 100, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

    public static <T> Future<T> submit(Callable<T> task) {
        return delegate.submit(task);
    }

    public static <T> Future<T> submit(Runnable task, T result) {
        return delegate.submit(task, result);
    }

    public static Future<?> submit(Runnable task) {
        return delegate.submit(task);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return delegate.invokeAll(tasks);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return delegate.invokeAll(tasks, timeout, unit);
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return delegate.invokeAny(tasks);
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegate.invokeAny(tasks, timeout, unit);
    }

    public static void execute(Runnable command) {
        delegate.execute(command);
    }
}
