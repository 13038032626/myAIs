package com.example.myais.executorPool;

import java.util.concurrent.*;

public class KimiPool {

    static ThreadPoolExecutor kimiExecutor;

    {
        int corePoolSize = 2;
        // 最大线程数
        int maximumPoolSize = 4;
        // 线程空闲时间
        long keepAliveTime = 10;
        // 时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        // 任务队列
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        // 线程工厂
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // 拒绝策略
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        kimiExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler);
    }
    public ThreadPoolExecutor getKimiExecutor(){
        return kimiExecutor;
    }

}
