package com.wenqi.learning.javayewu100.transaction.transactionrollbackfailed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author liangwq
 * @date 2021/1/4
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 异常无法传播出方法, 导致事务无法回滚
     *
     * @param name
     * @return
     */
    @Transactional
    public void createUserWrong1(String name) {
        try {
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        } catch (Exception e) {
            log.error("create user failed beacuse {}", e.getMessage());
        }
    }

    /**
     * 即使除了受检异常也无法让事务回滚
     * @param name
     */
    public void createUserWrong2(String name) throws IOException {
        userRepository.save(new UserEntity(name));
        otherTask();
    }

    @Transactional
    public void createUserRight1(String name) {
        try {
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        log.info("result {} ", userRepository.findByName(name).size());//为什么这里是1你能想明白吗？
    }

    //DefaultTransactionAttribute
    @Transactional(rollbackFor = Exception.class)
    public void createUserRight2(String name) throws IOException {
        userRepository.save(new UserEntity(name));
        otherTask();
    }

    /**
     * 因为文件不存在, 一定会抛出一个IOException
     * @throws IOException
     */
    private void otherTask() throws IOException {
        Files.readAllLines(Paths.get("file-that-not-exist"));
    }

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }

}
