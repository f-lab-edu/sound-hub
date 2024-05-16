package com.example.soundhub.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class WorkRequest {
	@Getter
	@Builder
	public static class addWork {
		@NotBlank(message = "제목을 입력해 주세요")
		private String name;
		@NotBlank(message = "카테고리를 입력해 주세요")
		private String workType;
		@NotBlank(message = "유튜브 주소를 입력해 주세요")
		private String youtubeUrl;
	}
}
