package com.wenqi.learning.javayewu100.nullvalue.pojonull;

import lombok.Data;

import javax.persistence.GeneratedValue;

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
