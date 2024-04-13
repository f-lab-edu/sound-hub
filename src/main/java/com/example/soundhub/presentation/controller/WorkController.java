package com.example.soundhub.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkController {
    @GetMapping("/tokenTest")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("good");
    }
}