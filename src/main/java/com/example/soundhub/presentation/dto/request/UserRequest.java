package com.example.soundhub.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserRequest {
	@Getter
	@Builder
	public static class join {

		@NotBlank(message = "이름을 입력해 주세요")
		@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자를 제외한 2~12 자리여야 합니다.")
		private String name;

		@NotBlank(message = "아이디를 입력해 주세요.")
		@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "아이디는 특수문자를 제외한 2~20 자리여야 합니다.")
		private String loginId;

		@NotBlank(message = "비밀번호를 입력해 주세요.")
		private String password;
	}

	@Getter
	@AllArgsConstructor
	public static class login {

		@NotBlank(message = "아이디를 입력해 주세요.")
		private String loginId;

		@NotBlank(message = "비밀번호를 입력해 주세요.")
		private String password;

	}

	@Getter
	public static class addProfile {
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

