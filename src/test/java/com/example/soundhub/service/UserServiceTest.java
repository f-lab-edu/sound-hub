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
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

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
                .loginId("testLoginId")
                .password("testPassword")
                .build();

        userRequest2 = UserRequest.join.builder()
                .name("testName2")
                .loginId("testLoginId2")
                .password("testPassword2")
                .build();

        user = User.builder()
                .name(userRequest.getName())
                .loginId(userRequest.getLoginId())
                .password(userRequest.getPassword())
                .build();

        user2 = User.builder()
                .name(userRequest2.getName())
                .loginId(userRequest2.getLoginId())
                .password(userRequest2.getPassword())
                .build();

    }

    @Test
    void registerUser_WithNonDuplicateId_ShouldSucceed() {
        // Given
        given(userMapper.existsByLoginId(anyString())).willReturn(false);
        given(userMapper.create(any(User.class))).willReturn(1);

        // When
        String registeredUserName = userService.registerUser(userRequest);
        String registeredUserName2 = userService.registerUser(userRequest2);

        // Then
        assertThat(registeredUserName).isEqualTo(user.getName());
        assertThat(registeredUserName2).isEqualTo(user2.getName());

        then(userMapper).should(times(2)).existsByLoginId(anyString());
        then(userMapper).should(times(2)).create(any(User.class));
    }

    @Test
    void registerUser_WithDuplicateId_ShouldThrowBadRequestException() {
        // Given
        given(userMapper.existsByLoginId(anyString())).willReturn(true);

        // When & Then
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> userService.registerUser(userRequest));

        then(userMapper).should(times(1)).existsByLoginId(anyString());
        then(userMapper).should(never()).create(any(User.class));
    }
}
