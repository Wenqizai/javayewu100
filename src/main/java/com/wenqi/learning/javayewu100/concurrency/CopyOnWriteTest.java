package com.wenqi.learning.javayewu100.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 测试CopyOnWrite在不同情况下的性能
 * (因为CopyOnWrite为数组复制形式来保证线程安全, 所以写多读少的场景性能不如
 * 加了同步锁的synchronizedList
 *
 * @author liangwq
 * @date 2020/12/7
 */
@Slf4j
@RestController
public class CopyOnWriteTest {

    /**
     * 高并发下写的性能
     * @return
     */
    @GetMapping("/write")
    public Map testWrite() {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        StopWatch stopWatch = new StopWatch();
        int loopCount = 100000;
        stopWatch.start("write:copyOnWriteArrayList");
        // 循环100000次并发往CopyOnWriteArrayList写入随机元素
        IntStream.rangeClosed(1, loopCount).parallel()
                .forEach(i -> copyOnWriteArrayList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        stopWatch.start("write:synchronizedList");
        // 循环100000次并发往加锁的ArrayList写入随机元素
        IntStream.rangeClosed(1, loopCount).parallel()
                .forEach(i -> synchronizedList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        Map result = new HashMap();
        result.put("copyOnWriteArrayList", copyOnWriteArrayList.size());
        result.put("synchronizedList", synchronizedList.size());
        return result;
    }


    /**
     * 帮助方法用来填充List
     * @param list
     */
    private void addAll(List<Integer> list) {
        list.addAll(IntStream.rangeClosed(1, 1000000).boxed().collect(Collectors.toList()));
    }

    /**
     * 创建并发读的性能
     *
     * @return
     */
    @GetMapping("/read")
    public Map testRead() {
        // 创建两个测试对象
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        // 填充数据
        addAll(copyOnWriteArrayList);
        addAll(synchronizedList);
        StopWatch stopWatch = new StopWatch();
        int loopCount = 1000000;
        int count = copyOnWriteArrayList.size();
        stopWatch.start("read:copyOnWriteArrayList");
        // 循环100000次并发往CopyOnWriteArrayList写入随机元素
        IntStream.rangeClosed(1, loopCount).parallel()
                .forEach(i -> copyOnWriteArrayList.get(ThreadLocalRandom.current().nextInt(count)));
        stopWatch.stop();
        stopWatch.start("read:synchronizedList");
        // 循环100000次并发往加锁的ArrayList写入随机元素
        IntStream.rangeClosed(1, loopCount).parallel()
                .forEach(i -> synchronizedList.get(ThreadLocalRandom.current().nextInt(count)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        Map result = new HashMap();
        result.put("copyOnWriteArrayList", copyOnWriteArrayList.size());
        result.put("synchronizedList", synchronizedList.size());
        return result;
    }

}

