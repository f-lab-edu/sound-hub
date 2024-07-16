package com.example.soundhub.presentation.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class UserResponse {
	@Getter
	@Builder
	public static class tokenInfo {
		@Schema(description = "토큰의 타입")
		private String grantType;
		@Schema(description = "액세스 토큰")
		private String accessToken;
		@Schema(description = "리프레시 토큰")
		private String refreshToken;
	}

	@Getter
	@Builder
	public static class viewProfile {
		@Schema(description = "해당 유저 프로필 인지 아닌지", example = "true")
		private boolean isMyProfile;
		@
		private String genre;
		private String position;
		private String introduce;
		private String backgroundImgUrl;
		private List<String> favoriteArtist;
	}
}
