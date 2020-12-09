package com.wenqi.learning.javayewu100.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * ConcurrentHashMap线程安全问题
 *
 * @author Wenqi Liang
 * @date 2020/11/18
 */
@Slf4j
@RestController
public class MyConcurrentHashMap {
    // 线程个数
    private static int THREAD_COUNT = 10;
    // 总元素数量
    private static int ITEM_COUNT = 1000;

    // 帮助方法, 用来获取一个指定元素数量模拟数据的ConcurrentHashMap
    private ConcurrentHashMap<String, Long> getData(int count) {
        return LongStream.rangeClosed(1, count)
                .boxed()
                .collect(Collectors.toConcurrentMap(i -> UUID.randomUUID().toString(), Function.identity(),
                        (o1, o2) -> o1, ConcurrentHashMap::new));
    }

    @GetMapping("/wrong")
    public String wrong() throws InterruptedException {
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        // 初始化900个元素
        log.info("init size{}:" + concurrentHashMap.size());
        // 使用线程池并发处理逻辑, 向map中添加元素
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            //查询还需要补充多少个元素
            int gap = ITEM_COUNT - concurrentHashMap.size();
            log.info("gap size:{}", gap);
            //补充元素
            concurrentHashMap.putAll(getData(gap));
        }));
        forkJoinPool.shutdown();
        //等待所有任务完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //最后元素个数会是1000吗？
        log.info("finish size:{}", concurrentHashMap.size());
        return "OK";
    }

    @GetMapping("/right2")
    public String right() throws InterruptedException {
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        // 初始化900个元素
        log.info("init size{}:" + concurrentHashMap.size());
        // 使用线程池并发处理逻辑, 向map中添加元素
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            synchronized (concurrentHashMap){
                //查询还需要补充多少个元素
                int gap = ITEM_COUNT - concurrentHashMap.size();
                log.info("gap size:{}", gap);
                //补充元素
                concurrentHashMap.putAll(getData(gap));
            }
        }));
        forkJoinPool.shutdown();
        //等待所有任务完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //最后元素个数会是1000吗？
        log.info("finish size:{}", concurrentHashMap.size());
        return "OK";
    }
}






