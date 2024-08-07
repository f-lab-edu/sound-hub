package com.example.soundhub.presentation.controller;

import java.util.List;

import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "WorkController", description = "작업물 관련 API")
@RestController
@RequestMapping("/works")
@RequiredArgsConstructor
public class WorkController {
	private final JwtUtil jwtUtil;

	private final WorkService workService;

	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "작업물 추가", description = "작업물 배경 이미지와 정보 입력, 성공 시 작업물 id 반환")
	public ResponseEntity<Long> addWork(@RequestHeader("Authorization") String token,
		@RequestPart(value = "image") MultipartFile image, @RequestPart("work") WorkRequest.addWork request) {
		Long userId = jwtUtil.extractUserId(token);

		Long workId = workService.addWork(request, userId, image);

		return ResponseEntity.ok(workId);
	}

	@GetMapping("/{userId}")
	@Operation(summary = "작업물 조회", description = "작업물 정보 조회")
	public ResponseEntity<List<WorkResponse.getWorksInfo>> viewUserWorks(@PathVariable Long userId) {
		List<WorkResponse.getWorksInfo> worksInfos = workService.viewUserWorks(userId);

		return ResponseEntity.ok(worksInfos);
	}

	@DeleteMapping("/{workId}/delete")
	@Operation(summary = "작업물 삭제", description = "작업물 삭제")
	public ResponseEntity<String> deleteMyWork(@PathVariable Long workId) {
		workService.deleteWork(workId);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{workId}/play")
	@Operation(summary = "작업물 재생", description = "작업물 재생")
	public ResponseEntity<String> playUserWork(@PathVariable Long workId) {
		String youtubeUrl = workService.playUserWork(workId);

		return ResponseEntity.ok(youtubeUrl);
	}

	@GetMapping("/{workId}/like")
	@Operation(summary = "작업물 좋아요", description = "작업물 좋아요")
	public ResponseEntity<Boolean> likeUserWork(@RequestHeader("Authorization") String token, @PathVariable Long workId) {
		Long userId = jwtUtil.extractUserId(token);

		boolean likes = workService.likeUserWork(workId, userId);

		return ResponseEntity.ok(likes);
	}
}