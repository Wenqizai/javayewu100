package com.wenqi.learning.javayewu100.httpinvoke.ribbonretry;

import com.wenqi.learning.javayewu100.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplicationNoRetry {

    public static void main(String[] args) {
        Utils.loadPropertySource(RibbonRetryIssueServerController.class, "ribbon-noretry.properties");
        SpringApplication.run(CommonMistakesApplicationNoRetry.class, args);
    }

}
