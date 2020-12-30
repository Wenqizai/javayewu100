package com.wenqi.learning.javayewu100.httpinvoke.feignandribbontimeout;

import com.wenqi.learning.javayewu100.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplicationByRibbon {

    public static void main(String[] args) {
        Utils.loadPropertySource(FeignAndRibbonController.class, "default.properties");
        Utils.loadPropertySource(FeignAndRibbonController.class, "ribbon.properties");
        SpringApplication.run(CommonMistakesApplicationByRibbon.class, args);
    }

}
