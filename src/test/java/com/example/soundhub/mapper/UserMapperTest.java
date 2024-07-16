package com.example.soundhub.mapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;

@TestPropertySource(locations = "classpath:application-test.yml")
@MybatisTest
public class UserMapperTest {

	@Autowired
	UserMapper userMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	User user;

	@BeforeEach
	public void setUp() {
		jdbcTemplate.update("INSERT INTO users (name, login_Id, password, profil_img_url) VALUES ('name', 'loginId', 'password', 'www.S3.com')");
		user = User.builder()
			.name("name")
			.loginId("loginId")
			.password("password")
			.profileImgUrl("www.S3.com")
			.build();
	}

	@AfterEach
	public void afterEach() {
		jdbcTemplate.update("TRUNCATE TABLE users");
	}

	@Test
	void create() {
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
	void findByUserId() {
		userMapper.create(user);
		User foundUser = userMapper.findById(user.getId());

		assertThat(user.getLoginId()).isEqualTo(foundUser.getLoginId());
	}
}

