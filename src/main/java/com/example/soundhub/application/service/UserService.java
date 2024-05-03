package com.example.soundhub.application.service;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.dao.UserDao;
import com.example.soundhub.jwt.JwtUtil;
import com.example.soundhub.presentation.dto.request.UserRequest;
import com.example.soundhub.presentation.dto.response.UserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final JwtUtil jwtUtil;

	private final UserDao userDao;

	@Transactional
	public String registerUser(UserRequest.join request) {
		User user = User.builder()
			.name(request.getName())
			.loginId(request.getLoginId())
			.password(request.getPassword())
			.build();

		User result = userDao.create(user);

		return result.getName();
	}

	@Transactional
	public UserResponse.tokenInfo login(UserRequest.login request) {
		String loginId = request.getLoginId();
		User user = userDao.findByLoginId(loginId);

		if (!user.getPassword().equals(request.getPassword())) {
			throw new BadRequestException(INVALID_PWD);
		}

		UserResponse.tokenInfo tokenInfo = jwtUtil.generateTokens(user.getId());

		return tokenInfo;
	}

}
