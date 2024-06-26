package com.example.soundhub.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class User {
	private final String name;
	private final String loginId;
	private final String password;
	private final String profileImgUrl;
	private Long id;

	@Builder
	public User(String name, String loginId, String password, String profileImgUrl) {
		this.name = name;
		this.loginId = loginId;
		this.password = password;
		this.profileImgUrl = profileImgUrl;
	}

	public void setId(Long id) {
		this.id = id;
	}
}