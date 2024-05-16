package com.example.soundhub.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Work {
	private Long id;
	private final Long userId;
	private final String name;
	private final String workType; // 추후 Enum 타입으로 수정
	private final String youtubeUrl;
	private final String workImageUrl;

	@Builder
	public Work(Long userId, String name, String workType, String youtubeUrl, String workImageUrl) {
		this.userId = userId;
		this.name = name;
		this.workType = workType;
		this.youtubeUrl = youtubeUrl;
		this.workImageUrl = workImageUrl;
	}
}
