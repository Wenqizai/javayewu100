package com.wenqi.learning.javayewu100.httpinvoke.feignandribbontimeout;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author liangwq
 * @date 2020/12/22
 */
@Configuration
@EnableFeignClients(basePackages = "com.wenqi.learning.javayewu100.httpinvoke.feignandribbontimeout")
public class AutoConfig {
}
