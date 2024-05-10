package com.example.soundhub.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.soundhub.application.service.WorkService;
import com.example.soundhub.jwt.JwtUtil;
import com.example.soundhub.presentation.dto.request.WorkRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkController {

	private final JwtUtil jwtUtil;

	private final WorkService workService;

	@PostMapping("/add")
	public ResponseEntity<Long> add(@RequestHeader("Authorization") String token,
		@RequestPart(value = "image") MultipartFile image,
		@RequestPart("work") WorkRequest.addWork request) {
		Long userId = jwtUtil.extractUserId(token);

		Long workId = workService.addWork(request, userId, image);

		return ResponseEntity.ok(workId);
	}

}