package com.example.soundhub.presentation.dto.response;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.soundhub.domain.Work;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class WorkResponse {
	@NoArgsConstructor
	@Data
	public static class getWorksInfo {
		private String name;

		private String workType;

		private String imgUrl;

		private String youtubeUrl;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
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
