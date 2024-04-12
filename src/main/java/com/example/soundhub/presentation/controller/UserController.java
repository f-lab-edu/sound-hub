package com.example.soundhub.presentation.controller;


import com.example.soundhub.application.service.UserService;
import com.example.soundhub.presentation.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserRequest.join request) {
        String userName = userService.registerUser(request);

        return ResponseEntity.ok(userName);
    }
}
