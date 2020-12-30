package com.wenqi.learning.javayewu100.httpinvoke.feignandribbontimeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Feign和Ribbon配置超时
 *
 * @author liangwq
 * @date 2020/12/22
 */
@RestController
@RequestMapping("feignandribbon")
@Slf4j
public class FeignAndRibbonController {

    @Autowired
    Client client;

    @PostMapping("/server")
    public void server() throws InterruptedException {
        TimeUnit.MINUTES.sleep(10);
    }

    @GetMapping("/client")
    public void timeout() {
        long begin = System.currentTimeMillis();
        try {
            client.server();
        } catch (Exception e) {
            log.warn("执行耗时: {}ms 错误: {}", System.currentTimeMillis() - begin, e.getMessage());
        }
    }


}
