package com.wenqi.learning.javayewu100.exception.finallyissue;

/**
 * @author liangwq
 * @date 2021/4/19
 */
public class TestResource implements AutoCloseable {
    @Override
    public void close() throws Exception {
        throw new Exception("close error");
    }

    public void read() throws Exception {
        throw new Exception("read error");
    }
}
