package com.example.soundhub.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import com.example.soundhub.application.service.UserService;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.dao.UserDao;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import com.example.soundhub.jwt.JwtUtil;
import com.example.soundhub.presentation.dto.request.UserRequest;

@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserMapper userMapper;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserService userService;

	@Test
	public void testRegisterUser_Success() {
		// Given
		UserRequest.join request = UserRequest.join.builder()
			.name("JohnDoe")
			.loginId("johndoe123")
			.password("password123")
			.build();

		User user = User.builder()
			.name(request.getName())
			.loginId(request.getLoginId())
			.password(request.getPassword())
			.build();

		// When
		when(userDao.create(user)).thenReturn(user);

		// Then
		//assertThat(userService.registerUser(request, image)).isEqualTo(user.getName());
	}

	@Test
	public void testLogin_Success() {
		// Given
		UserRequest.login request = new UserRequest.login("johndoe123", "password123");
		User user = new User("JohnDoe", "johndoe123", "password123", "www.S3.com");

		when(userDao.findByLoginId(request.getLoginId())).thenReturn(user);

		// Then
		assertThatCode(() -> userService.login(request))
			.doesNotThrowAnyException();
	}

}