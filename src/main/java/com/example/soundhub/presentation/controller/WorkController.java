package com.example.soundhub.presentation.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.soundhub.application.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkController {

	private final S3Service s3Service;

	@GetMapping("/tokenTest")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("good");
	}

	@PostMapping("/s3/upload")
	public ResponseEntity<?> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image) {
		String profileImage = "";
		if (image != null) {
			try {// 파일 업로드
				profileImage = s3Service.upload(image, "images");
				System.out.println("fileURL = " + profileImage);
			} catch (IOException e) {

			}
		}
		return ResponseEntity.ok(profileImage);
	}
}