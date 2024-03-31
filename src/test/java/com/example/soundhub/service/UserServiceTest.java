package com.example.soundhub.service;

import com.example.soundhub.application.service.UserService;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import com.example.soundhub.presentation.controller.UserController;
import com.example.soundhub.presentation.dto.request.UserRequest;
import com.example.soundhub.config.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;
    private UserRequest.join userRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userRequest = UserRequest.join.builder()
                .name("testName")
                .login_id("testLoginId")
                .password("testPassword")
                .build();

        user = User.builder()
                .name(userRequest.getName())
                .login_id(userRequest.getLogin_id())
                .password(userRequest.getPassword())
                .build();
    }

    @Test
    void registerUser_WithNonDuplicateId_ShouldSucceed() {
        // Given
        given(userMapper.idCheck(anyString())).willReturn(0);
        given(userMapper.register(any(User.class))).willReturn(1);

        // When
        String registeredUserName = userService.registerUser(userRequest);

        // Then
        assertEquals(user.getName(), registeredUserName);
        then(userMapper).should(times(1)).idCheck(anyString());
        then(userMapper).should(times(1)).register(any(User.class));
    }

    @Test
    void registerUser_WithDuplicateId_ShouldThrowBadRequestException() {
        // Given
        given(userMapper.idCheck(anyString())).willReturn(1);

        // When & Then
        assertThrows(BadRequestException.class, () -> userService.registerUser(userRequest));
        then(userMapper).should(times(1)).idCheck(anyString());
        then(userMapper).should(never()).register(any(User.class));
    }
}
