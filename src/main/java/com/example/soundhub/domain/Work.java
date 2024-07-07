package com.example.soundhub.domain;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Work {
	private final Long userId;
	private final String name;
	private final String workType; // 추후 Enum 타입으로 수정
	private final String youtubeUrl;
	private final String workImgUrl;
	private final LocalDate workCreatedDate;
	private Long numberOfPlays;
	private Long likes;
	private Long id;

	@Builder
	public Work(Long userId, String name, String workType, String youtubeUrl, String workImgUrl,
		LocalDate workCreatedDate, Long numberOfPlays, Long likes) {
		this.userId = userId;
		this.name = name;
		this.workType = workType;
		this.youtubeUrl = youtubeUrl;
		this.workImgUrl = workImgUrl;
		this.workCreatedDate = workCreatedDate;
		this.numberOfPlays = numberOfPlays;
		this.likes = likes;
	}

	public void incrementPlays() {
		this.numberOfPlays += 1;
	}
}
