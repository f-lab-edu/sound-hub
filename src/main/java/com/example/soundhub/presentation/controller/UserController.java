package com.example.soundhub.presentation.controller;

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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	private final JwtUtil jwtUtil;

	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestPart("image") MultipartFile image,
		@Validated @RequestPart("join") UserRequest.join request) {
		String userName = userService.registerUser(request, image);

		return ResponseEntity.ok(userName);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse.tokenInfo> login(@Validated @RequestBody UserRequest.login request) {
		UserResponse.tokenInfo tokenInfo = userService.login(request);

		return ResponseEntity.ok(tokenInfo);
	}

	@PostMapping("/add-profile")
	public ResponseEntity<Long> addProfile(@RequestHeader("Authorization") String token,
		@RequestPart("image") MultipartFile image, @RequestPart("profile") UserRequest.addProfile request) {
		Long userId = jwtUtil.extractUserId(token);

		Long profileId = userService.addProfile(request, userId, image);

		return ResponseEntity.ok(profileId);
	}

	@PutMapping("/change-profile")
	public ResponseEntity<Long> changeProfile(@RequestHeader("Authorization") String token,
		@RequestPart("image") MultipartFile image, @RequestPart("profile") UserRequest.addProfile request) {
		Long userId = jwtUtil.extractUserId(token);

		Long profileId = userService.changeProfile(request, userId, image);

		return ResponseEntity.ok(profileId);
	}

	@GetMapping("/profile/{userId}")
	public ResponseEntity<UserResponse.viewProfile> viewProfile(@RequestHeader("Authorization") String token,
		@PathVariable Long userId) {
		Long requestUser = jwtUtil.extractUserId(token);
		boolean isMyProfile = requestUser.equals(userId);

		UserResponse.viewProfile profile = userService.viewProfile(userId, isMyProfile);

		return ResponseEntity.ok(profile);
	}

}
