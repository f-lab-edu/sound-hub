package com.example.soundhub.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class WorkLike {
	private final Long workId;
	private final Long userId;
	private Long id;

	@Builder
	public WorkLike(Long workId, Long userId) {
		this.workId = workId;
		this.userId = userId;
	}
}
