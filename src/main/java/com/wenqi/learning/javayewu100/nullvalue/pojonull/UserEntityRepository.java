package com.wenqi.learning.javayewu100.nullvalue.pojonull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liangwq
 * @date 2021/2/18
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}