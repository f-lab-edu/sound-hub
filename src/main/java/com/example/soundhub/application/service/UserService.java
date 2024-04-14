package com.example.soundhub.application.service;


import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import com.example.soundhub.jwt.JwtUtil;
import com.example.soundhub.presentation.dto.request.UserRequest;
import com.example.soundhub.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final JwtUtil jwtUtil;

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
            log.error("Params : {}", user);
            throw new BadRequestException(REGISTER_USER_ERROR);
        }
        return user.getName();
    }

    @Transactional
    public UserResponse.tokenInfo login(UserRequest.login request){
        String loginId = request.getLoginId();

        try{
            User user = userMapper.findUserByLoginId(loginId);
            if(!user.getPassword().equals(request.getPassword())){
                throw new BadRequestException(INVALID_PWD);
            }
        } catch (DataAccessException e) {
            throw new BadRequestException(NOT_FOUND_USER);
        }

        UserResponse.tokenInfo tokenInfo = jwtUtil.generateTokens(loginId);

        return tokenInfo;
    }

    public boolean isDuplicatedId(String id) {
        return userMapper.existsByLoginId(id);
    }
}
