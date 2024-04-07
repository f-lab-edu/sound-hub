package com.example.soundhub.application.service;


import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import com.example.soundhub.presentation.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public String registerUser(UserRequest.join request) {
        boolean dupIdResult = isDuplicatedId(request.getLogin_id());
        if (dupIdResult) {
            throw new BadRequestException(DUPLICATE_ID);
        }

        User user = User.builder()
                .name(request.getName())
                .login_id(request.getLogin_id())
                .password(request.getPassword())
                .build();

        int insertCount = userMapper.register(user);
        if (insertCount != 1) {
            log.error("insertMember ERROR! {}", user);
            throw new RuntimeException(
                    "insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + user);
        }
        return user.getName();
    }


    public boolean isDuplicatedId(String id) {
        return userMapper.existsByLoginId(id);
    }
}
