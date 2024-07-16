package com.example.soundhub.presentation.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.soundhub.application.service.UserService;
import com.example.soundhub.jwt.JwtUtil;
import com.example.soundhub.presentation.dto.request.UserRequest;
import com.example.soundhub.presentation.dto.response.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "UserController", description = "회원가입, 로그인, 유저 프로필 관련 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	private final JwtUtil jwtUtil;

	@PostMapping(value = "/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "회원 가입", description = "프로필 이미지와 회원 가입 정보 입력, 성공 시 유저 이름 반환")
	public ResponseEntity<String> join(@RequestPart("image") MultipartFile image,
		@Validated @RequestPart("join") UserRequest.join request) {
		String userName = userService.registerUser(request, image);

		return ResponseEntity.ok(userName);
	}

	@PostMapping("/login")
	@Operation(summary = "로그인", description = "아이디와 비밀 번호 입력, 성공 시 토큰 반환")
	public ResponseEntity<UserResponse.tokenInfo> login(@Validated @RequestBody UserRequest.login request) {
		UserResponse.tokenInfo tokenInfo = userService.login(request);

		return ResponseEntity.ok(tokenInfo);
	}

	@PostMapping(value = "/add-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "프로필 추가", description = "프로필 정보 입력, 성공 시 프로필 id 반환")
	public ResponseEntity<Long> addProfile(@RequestHeader("Authorization") String token,
		@RequestPart("image") MultipartFile image, @RequestPart("profile") UserRequest.addProfile request) {
		Long userId = jwtUtil.extractUserId(token);

		Long profileId = userService.addProfile(request, userId, image);

		return ResponseEntity.ok(profileId);
	}

	@PutMapping(value = "/change-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "프로필 정보 수정", description = "프로필 정보 수정, 성공 시 프로필 id 반환")
	public ResponseEntity<Long> changeProfile(@RequestHeader("Authorization") String token,
		@RequestPart("image") MultipartFile image, @RequestPart("profile") UserRequest.addProfile request) {
		Long userId = jwtUtil.extractUserId(token);

		Long profileId = userService.changeProfile(request, userId, image);

		return ResponseEntity.ok(profileId);
	}

	@GetMapping("/profile/{userId}")
	@Operation(summary = "유저 프로필 조회", description = "프로필 정보 조회")
	public ResponseEntity<UserResponse.viewProfile> viewProfile(@RequestHeader("Authorization") String token,
		@PathVariable Long userId) {
		Long requestUser = jwtUtil.extractUserId(token);
		boolean isMyProfile = requestUser.equals(userId);

		UserResponse.viewProfile profile = userService.viewProfile(userId, isMyProfile);

		return ResponseEntity.ok(profile);
	}

}
