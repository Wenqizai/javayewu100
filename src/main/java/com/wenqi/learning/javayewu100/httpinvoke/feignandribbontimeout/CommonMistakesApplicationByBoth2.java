package com.wenqi.learning.javayewu100.httpinvoke.feignandribbontimeout;

import com.wenqi.learning.javayewu100.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplicationByBoth2 {

    public static void main(String[] args) {
        Utils.loadPropertySource(FeignAndRibbonController.class, "feignandribbon.properties");
        SpringApplication.run(CommonMistakesApplicationByBoth2.class, args);
    }

}
