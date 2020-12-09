package com.wenqi.learning.javayewu100.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 统计ConcurrentHashMap中key出现的次数
 *
 * @author Wenqi Liang
 * @date 2020/11/20
 */

@Slf4j
@RestController
public class CountMapKey {

    //循环次数
    private static int LOOP_COUNT = 10000000;
    //线程数量
    private static int THREAD_COUNT = 10;
    //元素数量
    private static int ITEM_COUNT = 1000;

    /**
     * 改进前: 每次对ConcurrentHashMap都加锁, 无法发挥ConcurrentHashMap的威力
     *
     * @return
     * @throws InterruptedException
     */
    private Map<String, Long> normalUse() throws InterruptedException {
        Map<String, Long> concurrentHashMap = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
            //获得一个随机的Key
            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            synchronized (concurrentHashMap) {
                if (concurrentHashMap.containsKey(key)) {
                    //Key存在则+1
                    concurrentHashMap.put(key, concurrentHashMap.get(key) + 1);
                } else {
                    //Key不存在则初始化为1
                    concurrentHashMap.put(key, 1L);
                }
            }
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return concurrentHashMap;
    }

    /**
     * 改进后: 借助原子类LongAdder和computeIfAbsent方法释放ConcurrentHashMap性能
     *
     * @return
     * @throws InterruptedException
     */
    private Map<String, Long> goodUser() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            // 利用computeIfAbsent()方法来实例化LongAdder，然后利用LongAdder来进行线程安全计数
            freqs.computeIfAbsent(key, v -> new LongAdder()).increment();
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        // 因为我们的Value是LongAddr而不是Long, 所以需要做一次转换才能返回
        return freqs.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().longValue(), (o1
                , o2) -> o1));
    }


    public static void main(String[] args) {
        CountMapKey countMapKey = new CountMapKey();
        // 改进前
        /*try {
            Map<String, Long> stringLongMap = countMapKey.normalUse();
            stringLongMap.forEach((o1, o2) -> {
                System.out.println("key:" + o1 + "->" + "value:" + o2);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // 改进后
        try {
            Map<String, Long> stringLongMap = countMapKey.goodUser();
            stringLongMap.forEach((k, v) -> {
                System.out.println("key:" + k + "->" + "value:" + v);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/good")
    public String good() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normaluse");
        Map<String, Long> normaluse = normalUse();
        stopWatch.stop();
        // 检验元素数量
        Assert.isTrue(normaluse.size() == ITEM_COUNT, "normal size err");
        // 校验累计总数
        Assert.isTrue(normaluse.entrySet().stream()
        .mapToLong(item -> item.getValue()).reduce(0, Long :: sum) == LOOP_COUNT, "normaluse count error");
        stopWatch.start("gooduse");
        Map<String, Long> goodUser = goodUser();
        stopWatch.stop();
        Assert.isTrue(goodUser.entrySet().stream()
                .mapToLong(item -> item.getValue()).reduce(0, Long :: sum) == LOOP_COUNT, "goodUser count error");
        log.info(stopWatch.prettyPrint());
        return "ok";
    }


}
