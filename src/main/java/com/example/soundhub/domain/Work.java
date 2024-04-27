package com.example.soundhub.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Work {
	private final Long userId;
	private final String name;
	private final WorkType workType;
	private final String youtubeUrl;
	private final String workImageUrl;
	private Long id;

	@Builder

	public Work(Long userId, String name, WorkType workType, String youtubeUrl, String workImageUrl) {
		this.userId = userId;
		this.name = name;
		this.workType = workType;
		this.youtubeUrl = youtubeUrl;
		this.workImageUrl = workImageUrl;
	}
}
