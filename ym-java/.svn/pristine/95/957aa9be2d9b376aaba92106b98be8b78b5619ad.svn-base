package com.lyht.business.abm.production.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @version: V1.0
 * @author: hjs
 * @className: ThreadPool
 * @packageName: com.lyht.business.abm.production.thread
 * @description: （类作用）
 * @data: 2020年02月27日 18:21
 * @see []
 **/
public class ThreadPool {
    //定义线程池信息
    public static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();

    public static ExecutorService pool = new ThreadPoolExecutor(2, 4,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
}
