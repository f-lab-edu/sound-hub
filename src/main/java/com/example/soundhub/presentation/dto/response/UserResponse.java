package com.example.soundhub.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

public class UserResponse {

    @Getter
    @Builder
    public static class tokenInfo {
        private String grantType;
        private String accessToken;
        private String refreshToken;
    }

}
