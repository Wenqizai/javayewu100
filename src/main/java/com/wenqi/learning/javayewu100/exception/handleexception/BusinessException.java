package com.wenqi.learning.javayewu100.exception.handleexception;

/**
 * @author liangwq
 * @date 2021/4/19
 */
public class BusinessException extends RuntimeException{

    private int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
