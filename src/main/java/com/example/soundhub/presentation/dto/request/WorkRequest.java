package com.example.soundhub.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

public class WorkRequest {
	@Getter
	@Builder
	public static class addWork {
		private String name;
		private String workType;
		private String youtubeUrl;
	}
}
