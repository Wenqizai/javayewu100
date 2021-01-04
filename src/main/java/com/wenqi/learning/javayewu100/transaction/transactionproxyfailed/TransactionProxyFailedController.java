package com.wenqi.learning.javayewu100.transaction.transactionproxyfailed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangwq
 * @date 2021/1/4
 */
@RestController
@RequestMapping("transactionproxyfailed")
@Slf4j
public class TransactionProxyFailedController {

    @Autowired
    private UserService userService;

    @GetMapping("/wrong1")
    public int wrong1(@RequestParam("name") String name) {
        return userService.createUserWrong1(name);
    }

    @GetMapping("/wrong2")
    public int wrong2(@RequestParam("name") String name) {
        return userService.createUserWrong2(name);
    }

    @GetMapping("/right1")
    public int right1(@RequestParam("name") String name) {
        return userService.createUserRight1(name);
    }

    @GetMapping("right2")
    public int right2(@RequestParam("name") String name) {
        try {
            userService.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userService.getUserCount(name);
    }

}
