package com.example.soundhub.service;

import com.example.soundhub.application.service.UserService;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.dao.UserDao;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import com.example.soundhub.jwt.JwtUtil;
import com.example.soundhub.presentation.dto.request.UserRequest;
import com.example.soundhub.config.exception.BadRequestException;
import org.springframework.dao.DuplicateKeyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
        when(userMapper.create(user)).thenReturn(1);

        // Then
        assertThat(userService.registerUser(request)).isEqualTo(user.getName());
    }


    @Test
    public void testLogin_Success() {
        // Given
        UserRequest.login request = new UserRequest.login("johndoe123", "password123");
        User user = new User("JohnDoe", "johndoe123", "password123");

        when(userDao.findByLoginId(request.getLoginId())).thenReturn(user);

        // Then
        assertThatCode(() -> userService.login(request))
                .doesNotThrowAnyException();
    }


}