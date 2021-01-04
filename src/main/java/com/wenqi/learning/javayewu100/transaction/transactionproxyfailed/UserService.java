package com.wenqi.learning.javayewu100.transaction.transactionproxyfailed;

import com.wenqi.learning.javayewu100.transaction.transactionproxyfailed.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liangwq
 * @date 2021/1/4
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService self;

    /**
     * 一个公共方法供Controller调用, 内部调用事务性的私有方法
     *
     * @param name
     * @return
     */
    public int createUserWrong1(String name) {
        try {
            this.createUserPrivate(new UserEntity(name));
        } catch (Exception e) {
            log.error("create user failed beacuse {}", e.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    public int createUserWrong2(String name) {
        try {
            this.createUserPublic(new UserEntity(name));
        } catch (Exception e) {
            log.error("create user failed beacuse {}", e.getMessage());
        }
        return userRepository.findByName(name).size();
    }

   public int createUserRight1(String name) {
        try {
            self.createUserPublic(new UserEntity(name));
        } catch (Exception e) {
            log.error("create user failed beacuse {}", e.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    /**
     * 标记了@Transactional的private方法
     *
     * @param entity
     */
    @Transactional
    private void createUserPrivate(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test")) {
            throw new RuntimeException("invalid username!!");
        }
    }

    /**
     * 标记了@Transactional的public方法
     *
     * @param entity
     */
    @Transactional
    public void createUserPublic(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test")) {
            throw new RuntimeException("invalid username!!");
        }
    }

    /**
     * 根据用户名查询用户数
     *
     * @param name
     * @return
     */
    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }
}
