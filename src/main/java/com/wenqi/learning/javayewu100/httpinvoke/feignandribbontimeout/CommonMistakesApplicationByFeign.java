package com.wenqi.learning.javayewu100.httpinvoke.feignandribbontimeout;

import com.wenqi.learning.javayewu100.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplicationByFeign {

    public static void main(String[] args) {
        Utils.loadPropertySource(FeignAndRibbonController.class, "default.properties");
        Utils.loadPropertySource(FeignAndRibbonController.class, "feign.properties");
        SpringApplication.run(CommonMistakesApplicationByFeign.class, args);
    }

}
