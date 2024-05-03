package com.example.soundhub.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.soundhub.application.service.WorkService;
import com.example.soundhub.presentation.dto.request.WorkRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkController {

	private final HttpServletRequest servletRequest;

	private final WorkService workService;

	@PostMapping("/add")
	public ResponseEntity<Long> add(@RequestPart(value = "image") MultipartFile image,
		@RequestPart("work") WorkRequest.addWork request) {
		Long userId = (Long)servletRequest.getAttribute("userId");

		Long workId = workService.addWork(request, userId, image);

		return ResponseEntity.ok(workId);
	}

}