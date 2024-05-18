package com.example.soundhub.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Work {
	private final Long userId;
	private final String name;
	private final String workType; // 추후 Enum 타입으로 수정
	private final String youtubeUrl;
	private final String workImgUrl;
	private final LocalDate workCreatedDate;
	private Long id;

	@Builder
	public Work(Long userId, String name, String workType, String youtubeUrl, String workImgUrl,
		LocalDate workCreatedDate) {
		this.userId = userId;
		this.name = name;
		this.workType = workType;
		this.youtubeUrl = youtubeUrl;
		this.workImgUrl = workImgUrl;
		this.workCreatedDate = workCreatedDate;
	}
}
