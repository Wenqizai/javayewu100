package com.wenqi.learning.javayewu100.lock.deadlock;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 商品(用于模拟下单流程)
 *
 * @author liangwq
 * @date 2020/12/9
 */
@Data
@RequiredArgsConstructor
public class Item {
    /**
     * 商品名
     */
    final String name;
    /**
     * 库存剩余
     */
    int remaining = 1000;
    /**
     * ToString不包含这个字段
     */
    @ToString.Exclude
    ReentrantLock lock = new ReentrantLock();
}
