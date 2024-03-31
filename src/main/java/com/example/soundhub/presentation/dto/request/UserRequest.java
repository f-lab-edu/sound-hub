package com.example.soundhub.presentation.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class UserRequest {
    @Getter
    @Builder
    public static class join {
        private String name;
        private String login_id;
        private String password;
    }

    @Getter
    public static class login {
        private String name;
        private String password;
    }
}

