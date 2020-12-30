package com.wenqi.learning.javayewu100.httpinvoke.feignandribbontimeout;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Wenqi Liang
 * @date 2020/12/22
 */
@FeignClient(name = "clientsdk")
public interface Client {

    @PostMapping("/feignandribbon/server")
    void server();

}
