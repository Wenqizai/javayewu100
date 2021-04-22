package com.wenqi.learning.javayewu100.exception.handleexception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liangwq
 * @date 2021/4/19
 */
@Data
@AllArgsConstructor
public class APIResponse<T> {
    private boolean success;
    private T data;
    private int code;
    private String message;
}
