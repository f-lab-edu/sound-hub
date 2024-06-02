package com.example.soundhub.domain;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class Profile {
	private final Long userId;
	private final String genre;
	private final String position;
	private final String introduce;
	private final String backgroundImgUrl;
	private final List<String> favoriteArtists;
	private Long id;

	@Builder
	public Profile(Long userId, String genre, String position, String introduce, String backgroundImgUrl,
		List<String> favoriteArtists) {
		this.userId = userId;
		this.genre = genre;
		this.position = position;
		this.introduce = introduce;
		this.backgroundImgUrl = backgroundImgUrl;
		this.favoriteArtists = favoriteArtists;
	}
}
