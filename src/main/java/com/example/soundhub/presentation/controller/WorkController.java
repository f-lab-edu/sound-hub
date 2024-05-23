package com.example.soundhub.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.soundhub.application.service.WorkService;
import com.example.soundhub.jwt.JwtUtil;
import com.example.soundhub.presentation.dto.request.WorkRequest;
import com.example.soundhub.presentation.dto.response.WorkResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/works")
@RequiredArgsConstructor
public class WorkController {

	private final JwtUtil jwtUtil;

	private final WorkService workService;

	@PostMapping("/add")
	public ResponseEntity<Long> addWork(@RequestHeader("Authorization") String token,
		@RequestPart(value = "image") MultipartFile image, @RequestPart("work") WorkRequest.addWork request) {
		Long userId = jwtUtil.extractUserId(token);

		Long workId = workService.addWork(request, userId, image);

		return ResponseEntity.ok(workId);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<WorkResponse.getWorksInfo>> viewUserWorks(@PathVariable Long userId) {
		List<WorkResponse.getWorksInfo> worksInfos = workService.viewUserWorks(userId);

		return ResponseEntity.ok(worksInfos);
	}

	@DeleteMapping("/{workId}")
	public ResponseEntity<String> deleteMyWork(@PathVariable Long workId) {
		workService.deleteWork(workId);

		return ResponseEntity.noContent().build();
	}

}