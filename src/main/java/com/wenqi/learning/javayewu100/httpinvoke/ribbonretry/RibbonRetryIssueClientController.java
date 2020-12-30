package com.wenqi.learning.javayewu100.httpinvoke.ribbonretry;

import com.wenqi.learning.javayewu100.httpinvoke.ribbonretry.feign.SmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author liangwq
 * @date 2020/12/29
 */
@RestController
@RequestMapping("ribbonretryissueclient")
@Slf4j
public class RibbonRetryIssueClientController {

    @Autowired
    private SmsClient smsClient;

    @GetMapping("/wrong")
    public String wrong() {
        log.info("client is called");
        try {
            smsClient.sendSmsWrong("13600000000", UUID.randomUUID().toString());
        } catch (Exception e) {
            log.error("send sms failed : {}", e.getMessage());
        }
        return "done";
    }

    @GetMapping("right")
    public String right() {
        try{
            smsClient.sendSmsRight("13600000000", UUID.randomUUID().toString());
        } catch (Exception ex) {
            log.error("send sms failed : {}", ex.getMessage());
        }
        return "done";
    }

}
