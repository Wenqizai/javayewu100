package com.wenqi.learning.javayewu100.transaction.transactionpropagation;

import com.wenqi.learning.javayewu100.transaction.transactionpropagation.UserEntity;
import com.wenqi.learning.javayewu100.transaction.transactionpropagation.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liangwq
 * @date 2021/1/4
 */
@Service
@Slf4j
public class SubUserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createSubUserWithExceptionWrong(UserEntity entity) {
        log.info("createSubUserWithExceptionWrong start");
        userRepository.save(entity);
        throw new RuntimeException("invalid status");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createSubUserWithExceptionRight(UserEntity entity) {
        log.info("createSubUserWithExceptionRight start");
        userRepository.save(entity);
        throw new RuntimeException("invalid status");
    }

}
