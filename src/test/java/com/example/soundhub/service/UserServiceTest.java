package com.example.soundhub.service;

import com.example.soundhub.application.service.UserService;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import com.example.soundhub.presentation.dto.request.UserRequest;
import com.example.soundhub.config.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;
    private UserRequest.join userRequest;
    private UserRequest.join userRequest2;
    private User user;

    private User user2;

    @BeforeEach
    void setUp() {
        userRequest = UserRequest.join.builder()
                .name("testName")
                .login_id("testLoginId")
                .password("testPassword")
                .build();

        userRequest2 = UserRequest.join.builder()
                .name("testName2")
                .login_id("testLoginId2")
                .password("testPassword2")
                .build();

        user = User.builder()
                .name(userRequest.getName())
                .login_id(userRequest.getLogin_id())
                .password(userRequest.getPassword())
                .build();

        user2 = User.builder()
                .name(userRequest2.getName())
                .login_id(userRequest2.getLogin_id())
                .password(userRequest2.getPassword())
                .build();

    }

    @Test
    void registerUser_WithNonDuplicateId_ShouldSucceed() {
        // Given
        given(userMapper.existsByLoginId(anyString())).willReturn(false);
        given(userMapper.register(any(User.class))).willReturn(1);

        // When
        String registeredUserName = userService.registerUser(userRequest);
        String registeredUserName2 = userService.registerUser(userRequest2);

        // Then
        assertEquals(user.getName(), registeredUserName);
        assertEquals(user2.getName(), registeredUserName2);

        then(userMapper).should(times(2)).existsByLoginId(anyString());
        then(userMapper).should(times(2)).register(any(User.class));
    }

    @Test
    void registerUser_WithDuplicateId_ShouldThrowBadRequestException() {
        // Given
        given(userMapper.existsByLoginId(anyString())).willReturn(true);

        // When & Then
        assertThrows(BadRequestException.class, () -> userService.registerUser(userRequest));
        then(userMapper).should(times(1)).existsByLoginId(anyString());
        then(userMapper).should(never()).register(any(User.class));
    }
}
