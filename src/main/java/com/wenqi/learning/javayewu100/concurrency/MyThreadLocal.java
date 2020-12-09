package com.wenqi.learning.javayewu100.concurrency;

import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread并发下的脏数据
 *
 * @author Wenqi Liang
 * @date 2020/11/17
 */
@RestController
public class MyThreadLocal {

    private ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    private ThreadLocal<DateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));


    @GetMapping("/error")
    public String wrong(@RequestParam("userId")Integer userId) {
        // 设置用户信息之前先查询一次ThreadLocal中的用户信息
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        // 设置用户信息
        currentUser.set(userId);
        // 设置用户信息之后再查询一次ThreadLocal中的用户信息
        String after = Thread.currentThread().getName() + ":" + currentUser.get();
        System.out.println(before);
        System.out.println(after);
        return "before:" + before + "\n" + "after:" + after;
    }

    @GetMapping("/right")
    public String right(@RequestParam("userId")Integer userId) {
         // 设置用户信息之前先查询一次ThreadLocal中的用户信息
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        // 设置用户信息
        currentUser.set(userId);
        try {
            // 设置用户信息之后再查询一次ThreadLocal中的用户信息
            String after = Thread.currentThread().getName() + ":" + currentUser.get();
            System.out.println(before);
            System.out.println(after);
            return "before:" + before + "\n" + "after:" + after;
        } finally {
            currentUser.remove();
        }
    }




}
