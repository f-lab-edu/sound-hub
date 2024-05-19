package com.example.soundhub.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Profile {
	private final Long userId;
	private final String genre;
	private final String position;
	private final String introduce;
	private final String backgroundImgUrl;
	private final String favoriteArtistFirst;
	private final String favoriteArtistSecond;
	private final String favoriteArtistThird;
	private final String favoriteArtistFourth;
	private final String favoriteArtistFifth;
	private Long id;

	@Builder
	public Profile(Long userId, String genre, String position, String introduce, String backgroundImgUrl,
		String favoriteArtistFirst, String favoriteArtistSecond, String favoriteArtistThird,
		String favoriteArtistFourth,
		String favoriteArtistFifth) {
		this.userId = userId;
		this.genre = genre;
		this.position = position;
		this.introduce = introduce;
		this.backgroundImgUrl = backgroundImgUrl;
		this.favoriteArtistFirst = favoriteArtistFirst;
		this.favoriteArtistSecond = favoriteArtistSecond;
		this.favoriteArtistThird = favoriteArtistThird;
		this.favoriteArtistFourth = favoriteArtistFourth;
		this.favoriteArtistFifth = favoriteArtistFifth;
	}
}
