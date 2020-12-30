package com.wenqi.learning.javayewu100.httpinvoke.ribbonretry.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liangwq
 * @date 2020/12/29
 */
@FeignClient(name = "SmsClient")
public interface SmsClient {

    @GetMapping("/ribbonretryissueserver/sms")
    public void sendSmsWrong(@RequestParam("mobile")String mobile,
                             @RequestParam("message") String message);

    @PostMapping("/ribbonretryissueserver/sms")
    public void sendSmsRight(@RequestParam("mobile")String mobile,
                             @RequestParam("message") String message);
}
