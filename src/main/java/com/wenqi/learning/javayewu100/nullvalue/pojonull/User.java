package com.wenqi.learning.javayewu100.nullvalue.pojonull;

import lombok.Data;
import org.hibernate.annotations.Entity;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author liangwq
 * @date 2021/2/18
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private Integer age;
    private Date createDate = new Date();
}
