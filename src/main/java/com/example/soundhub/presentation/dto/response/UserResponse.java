package com.example.soundhub.presentation.dto.response;

import java.util.List;

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

	@Getter
	@Builder
	public static class viewProfile {
		private boolean isMyProfile;
		private String genre;
		private String position;
		private String introduce;
		private String backgroundImgUrl;
		private List<String> favoriteArtist;
	}
}
