package com.example.soundhub.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.example.soundhub.domain.User;

@Mapper
@Repository
public interface UserMapper {
	@Select("SELECT * FROM users WHERE id = #{id}")
	User findById(Long id);

	@Select("SELECT * FROM users WHERE login_id = #{loginId}")
	User findByLoginId(String loginId);

	int create(User user);

	@Select("SELECT EXISTS(SELECT 1 FROM users WHERE login_id = #{loginId})")
	boolean existsByLoginId(String loginId);
}

