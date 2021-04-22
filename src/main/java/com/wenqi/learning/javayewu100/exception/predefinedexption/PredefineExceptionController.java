package com.wenqi.learning.javayewu100.exception.predefinedexption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangwq
 * @date 2021/4/20
 */
@RestController
@Slf4j
@RequestMapping("predefinedexception")
public class PredefineExceptionController {

    @GetMapping("wrong")
    public void wrong() {
        try {
            createOrderWrong();
        } catch (Exception ex) {
            log.error("createOrder got error", ex);
        }

        try {
            cancelOrderWrong();
        } catch (Exception ex) {
            log.error("cancelOrderWrong got error", ex);
        }

    }

    @GetMapping("right")
    public void right() {
        try {
            createOrderRight();
        } catch (Exception ex) {
            log.error("createOrder got error", ex);
        }

        try {
            cancelOrderRight();
        } catch (Exception ex) {
            log.error("cancelOrderWrong got error", ex);
        }

    }

    private void createOrderWrong() {
        System.out.println("create");
        throw Exceptions.ORDEREXISTS;
    }

    private void cancelOrderWrong() {
        System.out.println("cancel");
        throw Exceptions.ORDEREXISTS;
    }

    private void createOrderRight() {
        System.out.println("create");
        throw Exceptions.orderExists();
    }

    private void cancelOrderRight() {
        System.out.println("cancel");
        throw Exceptions.orderExists();
    }
}

