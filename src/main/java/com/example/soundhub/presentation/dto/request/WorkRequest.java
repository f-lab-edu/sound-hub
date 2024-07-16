package com.example.soundhub.presentation.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class WorkRequest {
	@Getter
	@Builder
	@Schema(title = "WORK_REQ_01 : 작업물 추가 요청 DTO")
	public static class addWork {
		@NotBlank(message = "제목을 입력해 주세요")
		@Schema(description = "작업물 제목", example = "보고싶다")
		private String name;

		@NotBlank(message = "카테고리를 입력해 주세요")
		@Schema(description = "작업물 카테고리(곡, 앨범, 연주)", example = "곡")
		private String workType;

		@NotBlank(message = "유튜브 주소를 입력해 주세요")
		@Schema(description = "유튜브 주소", example = "https://www.youtube.com/watch?v=ddRtTo8iiCU")
		private String youtubeUrl;

		@NotBlank(message = "작업물의 발매 날짜를 입력해주세요")
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@Schema(description = "작업물 발매 날짜", example = "2020-03-29")
		private LocalDate workCreatedDate;
	}
}
