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


@TestPropertySource(locations = "classpath:application-test.yml")
@MybatisTest
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    User user = User.builder().name("name").login_id("login_id").password("password").build();

    @AfterEach
    public void afterEach() {
        jdbcTemplate.update("TRUNCATE TABLE Users");
    }

    @Test
    void register() {
        int success = userMapper.register(user);

        Assertions.assertEquals(1, success);
    }

    @Test
    void idCheck() {
        userMapper.register(user);
        boolean exist = userMapper.existsByLoginId(user.getLogin_id());

        Assertions.assertEquals(true, exist);
    }

    @Test
    void findByUserIdx() {
        userMapper.register(user);
        User foundUser = userMapper.findUserById(user.getId());

        Assertions.assertEquals(user, foundUser);
    }

}
