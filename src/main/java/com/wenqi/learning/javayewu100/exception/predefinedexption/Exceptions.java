package com.wenqi.learning.javayewu100.exception.predefinedexption;

/**
 * @author liangwq
 * @date 2021/4/20
 */
public class Exceptions {

    public static BusinessException ORDEREXISTS = new BusinessException("订单已经存在", 3001);

    public static BusinessException orderExists() {
        return new BusinessException("订单已经存在", 3001);
    }

}
