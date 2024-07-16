package com.example.soundhub.presentation.dto.response;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.soundhub.domain.Work;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class WorkResponse {
	@NoArgsConstructor
	@Data
	@Schema(title = "WORK_RES_01 : 작업물 조회 응답 DTO")
	public static class getWorksInfo {
		@Schema(description = "작업물 제목", example = "보고싶다")
		private String name;

		@Schema(description = "작업물 카테고리(곡, 앨범, 연주)", example = "곡")
		private String workType;

		@Schema(description = "작업물 이미지 URL", example = "https://www.S3.com/watch?v=ddRtTo8iiCU")
		private String imgUrl;

		@Schema(description = "유튜브 주소", example = "https://www.youtube.com/watch?v=ddRtTo8iiCU")
		private String youtubeUrl;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@Schema(description = "작업물 발매 날짜", example = "2020-03-29")
		private LocalDate workCreatedDate;

		@Builder
		public getWorksInfo(String name, String workType, String imgUrl, String youtubeUrl, LocalDate workCreatedDate) {
			this.name = name;
			this.workType = workType;
			this.imgUrl = imgUrl;
			this.youtubeUrl = youtubeUrl;
			this.workCreatedDate = workCreatedDate;
		}

		public static getWorksInfo toDomain(Work work) {
			return getWorksInfo.builder()
				.name(work.getName())
				.workType(work.getWorkType())
				.imgUrl(work.getWorkImgUrl())
				.youtubeUrl(work.getYoutubeUrl())
				.workCreatedDate(work.getWorkCreatedDate())
				.build();
		}
	}
}
