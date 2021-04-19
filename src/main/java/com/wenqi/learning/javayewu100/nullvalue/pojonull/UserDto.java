package com.wenqi.learning.javayewu100.nullvalue.pojonull;

import lombok.Data;

import java.util.Optional;

/**
 * @author liangwq
 * @date 2021/2/18
 */
@Data
public class UserDto {
    private Long id;
    private Optional<String> name;
    private Optional<Integer> age;
}
