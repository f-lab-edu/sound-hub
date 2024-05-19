package com.example.soundhub.application.service;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.soundhub.config.exception.AwsS3Exception;
import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.domain.Profile;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.dao.ProfileDao;
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

	private final ProfileDao profileDao;

	private final S3Service s3Service;

	@Transactional
	public String registerUser(UserRequest.join request, MultipartFile image) {
		String imgUrl = null;

		if (image.getSize() > 0) {
			try {
				imgUrl = s3Service.upload(image, "images");
			} catch (IOException e) {
				throw new AwsS3Exception(IMAGE_UPLOAD_ERROR);
			}
		}

		User user = User.builder()
			.name(request.getName())
			.loginId(request.getLoginId())
			.password(request.getPassword())
			.profileImgUrl(imgUrl)
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

	@Transactional
	public Long addProfile(UserRequest.addProfile request, Long userId, MultipartFile image) {
		String imgUrl = null;

		if (image.getSize() > 0) {
			try {
				imgUrl = s3Service.upload(image, "images");
			} catch (IOException e) {
				throw new AwsS3Exception(IMAGE_UPLOAD_ERROR);
			}
		}

		Profile profile = Profile.builder()
			.userId(userId)
			.genre(request.getGenre())
			.position(request.getPosition())
			.introduce(request.getIntroduce())
			.backgroundImgUrl(imgUrl)
			.favoriteArtistFirst(request.getFavoriteArtistFirst())
			.favoriteArtistSecond(request.getFavoriteArtistSecond())
			.favoriteArtistThird(request.getFavoriteArtistThird())
			.favoriteArtistFifth(request.getFavoriteArtistFourth())
			.favoriteArtistFifth(request.getFavoriteArtistFifth())
			.build();

		Profile Result = profileDao.create(profile);

		return Result.getId();
	}

}
