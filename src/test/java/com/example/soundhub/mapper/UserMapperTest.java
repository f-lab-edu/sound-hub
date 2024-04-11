package com.example.soundhub.mapper;


import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;


@TestPropertySource(locations = "classpath:application-test.yml")
@MybatisTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    User user = User.builder().name("name").loginId("loginId").password("password").build();

    @AfterEach
    public void afterEach() {
        jdbcTemplate.update("TRUNCATE TABLE users");
    }

    @Test
    void register() {
        int success = userMapper.create(user);

        assertThat(1).isEqualTo(success);
    }

    @Test
    void existsByLoginId() {
        userMapper.create(user);
        boolean exist = userMapper.existsByLoginId(user.getLoginId());

        assertThat(true).isEqualTo(exist);
    }

    @Test
    void findByUserIdx() {
        userMapper.create(user);
        User foundUser = userMapper.findUserById(user.getId());

        System.out.println(foundUser.getId());
        System.out.println(foundUser.getLoginId());
        System.out.println(foundUser.getName());

        assertThat(user.getLoginId()).isEqualTo(foundUser.getLoginId());
    }
}
