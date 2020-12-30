package com.wenqi.learning.javayewu100.httpinvoke.ribbonretry;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author liangwq
 * @date 2020/12/22
 */
@Configuration
@EnableFeignClients(basePackages = "com.wenqi.learning.javayewu100.httpinvoke.ribbonretry.feign")
public class AutoConfig {
}
