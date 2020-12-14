package com.wenqi.learning.javayewu100.lock;

import com.wenqi.learning.javayewu100.lock.deadlock.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author liangwq
 * @date 2020/12/9
 */
@Slf4j
@RestController
public class Controller {

    //###################### 操作静态字段加锁方法 ######################//
    /**
     * 在非静态的 wrong 方法上加锁，只能确保多个线程无法执行同一个实例的 wrong 方法，
     * 却不能保证不会执行不同实例的 wrong 方法。而静态的 counter 在多个实例中共享，所以
     * 必然会出现线程安全问题
     *
     * @param count
     * @return
     */
    @GetMapping("/staticLock/wrong")
    public int wrong(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        // 多线程循环一定次数调用Data类不同实例的wrong方法
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().wrong());
        return Data.getCounter();
    }

    /**
     * 对于操作的right方法加入静态同步锁locker
     *
     * @param count
     * @return
     */
    @GetMapping("/staticLock/right")
    public int right(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        // 多线程循环一定次数调用Data类不同实例的right方法
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().right());
        return Data.getCounter();
    }

    //###################### 滥用锁会影响性能 ######################//

    private List<Integer> data = new ArrayList<>();
    /**
     * 不涉及共享资源的慢方法
     */
    private void slow() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 错误的加锁方法
     * @return
     */
    @GetMapping("/abuseLock/wrong")
    public int wrong() {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).parallel().forEach(i -> {
            //加锁粒度太粗了
            synchronized (this) {
                slow();
                data.add(i);
            }
        });
        log.info("took:{}", System.currentTimeMillis() - begin);
        return data.size();
    }

    /**
     * 正确的加锁方法
     * @return
     */
    @GetMapping("/abuseLock/right")
    public int right() {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).parallel().forEach(i -> {
            slow();
            //只对List加锁
            synchronized (data) {
                data.add(i);
            }
        });
        log.info("took:{}", System.currentTimeMillis() - begin);
        return data.size();
    }
}
