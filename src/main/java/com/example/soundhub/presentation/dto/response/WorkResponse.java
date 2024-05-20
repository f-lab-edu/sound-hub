package com.example.soundhub.presentation.dto.response;

import java.time.LocalDate;

import com.example.soundhub.domain.Work;
import com.fasterxml.jackson.annotation.JsonFormat;

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

		@JsonFormat(pattern = "yyyy-MM-dd")
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
				.imgUrl(work.getWorkImageUrl())
				.youtubeUrl(work.getYoutubeUrl())
				.workCreatedDate(work.getWorkCreatedDate())
				.build();
		}
	}
}
