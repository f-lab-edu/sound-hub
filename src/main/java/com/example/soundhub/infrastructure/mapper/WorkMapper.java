package com.example.soundhub.infrastructure.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.example.soundhub.domain.Work;

@Mapper
@Repository
public interface WorkMapper {
	int create(Work work);

	@Select("SELECT * FROM works WHERE id = #{id}")
	Work findById(Long id);

	@Select("SELECT * FROM works WHERE user_id = #{userId}")
	List<Work> findAllWorksByUserId(Long userId);

	@Delete("DELETE FROM works WHERE id = #{workId}")
	void deleteWork(Long workId);

	@Update("UPDATE works SET number_of_plays = number_of_plays + 1 WHERE id = #{id}")
	int updateNumberOfPlays(Long id);
}
