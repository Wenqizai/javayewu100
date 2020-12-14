package com.wenqi.learning.javayewu100.lock.deadlock;

import lombok.Getter;

/**
 * @author liangwq
 * @date 2020/12/9
 */
public class Data {
    @Getter
    private static int counter = 0;
    private static Object locker = new Object();

    public static int reset() {
        counter = 0;
        return counter;
    }

    public synchronized void wrong() {
            counter++;
    }

    public synchronized void right() {
        synchronized (locker) {
            counter++;
        }
    }
}
