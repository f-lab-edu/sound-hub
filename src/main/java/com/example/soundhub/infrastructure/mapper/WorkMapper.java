package com.example.soundhub.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.example.soundhub.domain.Work;

@Mapper
@Repository
public interface WorkMapper {
	int create(Work work);

	@Select("SELECT * FROM works WHERE id = #{id}")
	Work findWorkById(Long id);
}
