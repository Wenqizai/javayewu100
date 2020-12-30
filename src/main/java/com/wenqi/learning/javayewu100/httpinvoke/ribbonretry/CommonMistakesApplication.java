package com.wenqi.learning.javayewu100.httpinvoke.ribbonretry;

import com.wenqi.learning.javayewu100.common.Utils;
import com.wenqi.learning.javayewu100.httpinvoke.feignandribbontimeout.FeignAndRibbonController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        Utils.loadPropertySource(RibbonRetryIssueServerController.class, "default-ribbon.properties");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

}
