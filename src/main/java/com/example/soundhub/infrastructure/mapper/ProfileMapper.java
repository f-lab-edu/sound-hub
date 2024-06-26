package com.example.soundhub.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.example.soundhub.domain.Profile;

@Mapper
@Repository
public interface ProfileMapper {
	int create(Profile profile);

	@Select("SELECT * FROM profiles WHERE id = #{id}")
	Profile findById(Long id);

	int update(Profile profile);

	@Select("SELECT id, user_id, genre, position, introduce, background_img_url, favorite_artists FROM profiles WHERE user_id = #{userId}")
	Profile findByUserId(Long userId);
}
