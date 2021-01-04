package com.wenqi.learning.javayewu100.transaction.transactionpropagation;


import com.wenqi.learning.javayewu100.transaction.transactionpropagation.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangwq
 * @date 2021/1/4
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByName(String name);
}
