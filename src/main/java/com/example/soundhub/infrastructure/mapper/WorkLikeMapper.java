package com.example.soundhub.infrastructure.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.example.soundhub.domain.WorkLike;

@Mapper
@Repository
public interface WorkLikeMapper {

	int create(WorkLike workLike);

	@Select("SELECT * FROM work_likes WHERE id = #{id}")
	WorkLike findById(Long id);

	@Select("SELECT COUNT(*) > 0 FROM work_likes WHERE user_id = #{userId} AND work_id = #{workId}")
	boolean doesWorkLikeExistForUser(Long userId, Long workId);

	@Delete("DELETE FROM work_likes WHERE user_id = #{userId} AND work_id = #{workId}")
	int deleteByUserIdAndWorkId(Long userId, Long workId);
}
