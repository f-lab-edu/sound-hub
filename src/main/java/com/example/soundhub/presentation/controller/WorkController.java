package com.example.soundhub.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.soundhub.application.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkController {

	private final S3Service s3Service;

}