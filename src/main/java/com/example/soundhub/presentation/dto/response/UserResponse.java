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

	@Getter
	@Builder
	public static class viewProfile {
		private boolean isMyProfile;
		private String genre;
		private String position;
		private String introduce;
		private String backgroundImgUrl;
		private String favoriteArtistFirst;
		private String favoriteArtistSecond;
		private String favoriteArtistThird;
		private String favoriteArtistFourth;
		private String favoriteArtistFifth;
	}
}
