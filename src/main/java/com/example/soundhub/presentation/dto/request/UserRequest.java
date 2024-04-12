package com.example.soundhub.presentation.dto.request;


import lombok.Builder;
import lombok.Getter;


public class UserRequest {
    @Getter
    @Builder
    public static class join {
        private String name;
        private String loginId;
        private String password;
    }

    @Getter
    public static class login {
        private String name;
        private String password;
    }
}

