package com.example.soundhub.application.service;


import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import com.example.soundhub.presentation.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.soundhub.config.exception.ErrorResponseStatus.DUPLICATE_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public String registerUser(UserRequest.join request) {
        boolean dupIdResult = isDuplicatedId(request.getLoginId());
        if (dupIdResult) {
            throw new BadRequestException(DUPLICATE_ID);
        }

        User user = User.builder().name(request.getName()).loginId(request.getLoginId()).password(request.getPassword()).build();

        try {
            userMapper.create(user);
        } catch (DataAccessException e) {
            log.error("insertMember ERROR! {}", e.getMessage());
            throw new RuntimeException("insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + user, e);
        }
        return user.getName();
    }


    public boolean isDuplicatedId(String id) {
        return userMapper.existsByLoginId(id);
    }
}
