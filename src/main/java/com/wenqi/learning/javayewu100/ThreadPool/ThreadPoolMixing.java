package com.wenqi.learning.javayewu100.ThreadPool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * 不同业务场景, 采用不同线程池
 * 1. 吞吐量小, 执行慢, IO任务多 -> 更多的线程数, 不需要大队列
 * 2. 吞吐量大, 执行快 -> 小量线程数, 较大的队列
 * 一般情况: 线程数 = CPU核数或核数 * 2 (减少CPU线程切换带来的消耗)
 *
 * @author liangwq
 * @date 2020/12/11
 */
@Slf4j
@RestController
public class ThreadPoolMixing {

    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            2, 2,
            1, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("batchfileprocess-threadpool-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @GetMapping("/threadpool/mix/wrong")
    public int wrong() throws ExecutionException, InterruptedException {
        return threadPool.submit(calcTask()).get();
    }

    private static ThreadPoolExecutor asyncCalcThreadPool = new ThreadPoolExecutor(
        200,200,
        1, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNameFormat("asynccalc-threadpool-%d").build());

    @GetMapping("/threadpool/mix/right")
    public int right() throws ExecutionException, InterruptedException {
        return asyncCalcThreadPool.submit(calcTask()).get();
    }

    private Callable<Integer> calcTask() {
        return () -> {
            TimeUnit.MILLISECONDS.sleep(10);
            return 1;
        };
    }

    //    @PostConstruct
    public void init() {
        printStats(threadPool);
        new Thread(() -> {
            // 模拟需要写入大量数据
            String payload = IntStream.rangeClosed(1, 1000000)
                    .mapToObj(__ -> "a")
                    .collect(Collectors.joining(""));
            while (true) {
                threadPool.execute(() -> {
                    try {
                        Files.write(Paths.get("demo.txt"),
                                Collections.singletonList(LocalTime.now().toString() + ":" + payload), UTF_8, CREATE,
                                TRUNCATE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();
    }

    /**
     * 打印线程信息
     *
     * @param threadPool
     */
    private void printStats(ThreadPoolExecutor threadPool) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("=========================");
            log.info("Pool Size: {}", threadPool.getPoolSize());
            log.info("Active Threads: {}", threadPool.getActiveCount());
            log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
            log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());

            log.info("=========================");
        }, 0, 1, TimeUnit.SECONDS);
    }
}
