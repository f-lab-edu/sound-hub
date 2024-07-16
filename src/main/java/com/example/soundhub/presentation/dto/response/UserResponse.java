package com.example.soundhub.presentation.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class UserResponse {
	@Getter
	@Builder
	@Schema(title = "USER_RES_01 : 로그인 응답 DTO (토큰)")
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
	@Schema(title = "USER_RES_02 : 프로필 조회 응답 DTO")
	public static class viewProfile {
		@Schema(description = "해당 유저 프로필 인지 아닌지", example = "true")
		private boolean isMyProfile;

		@Schema(description = "해당 유저의 장르", example = "발라드")
		private String genre;

		@Schema(description = "해당 유저의 포지션", example = "보컬")
		private String position;

		@Schema(description = "해당 유저의 자기 소개", example = "안녕하세요")
		private String introduce;

		@Schema(description = "배경 이미지 URL")
		private String backgroundImgUrl;

		@Schema(description = "사용자의 선호 아티스트 (최대 5명)", example = "[\"김건모\", \"마이클잭슨\", \"김범수\"]")
		private List<String> favoriteArtist;
	}
}
