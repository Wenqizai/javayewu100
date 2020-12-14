package com.wenqi.learning.javayewu100.ThreadPool;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 考虑线程池复用情况
 *
 * @author liangwq
 * @date 2020/12/11
 */
@RestController
public class ThreadPoolReUse {

    @GetMapping("/threadpool/wrong")
    public String wrong() throws InterruptedException {
//        ThreadPoolExecutor threadPool = ThreadPoolWrongHelper.getThreadPool();
        ThreadPoolExecutor threadPool = ThreadPoolRightHelper.getThreadPool();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
            });
        });
        return "OK";
    }
}

/**
 * 模拟提供线程池的工具类(错误)
 */
class ThreadPoolWrongHelper {
    public static ThreadPoolExecutor getThreadPool() {
        // 线程池没有被复用, 每次都创建一个newCachedThreadPool
        return (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }
}

/**
 * 模拟提供线程池的工具类(优秀)
 */
class ThreadPoolRightHelper {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            10, 50,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            new ThreadPoolExecutor.AbortPolicy());

    public static ThreadPoolExecutor getThreadPool() {
        // 线程池被复用
        return threadPoolExecutor;
    }
}
