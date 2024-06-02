package com.example.soundhub.mapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import com.example.soundhub.domain.Work;
import com.example.soundhub.infrastructure.mapper.WorkMapper;

@TestPropertySource(locations = "classpath:application-test.yml")
@MybatisTest
public class WorkMapperTest {

	@Autowired
	WorkMapper workMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	Work work = Work.builder().
		userId(1L).
		name("work1").
		workType("SONG").
		youtubeUrl("www.youtube.com/ww1").
		workImgUrl("www.s3.com/1112").
		build();

	@AfterEach
	public void afterEach() {
		jdbcTemplate.update("TRUNCATE TABLE works");
	}

	@Test
	void create() {
		int success = workMapper.create(work);

		assertThat(1).isEqualTo(success);
	}

	@Test
	void findByWorkId() {
		workMapper.create(work);
		Work foundWork = workMapper.findById(work.getId());

		assertThat(work.getName()).isEqualTo(foundWork.getName());
		assertThat(work.getWorkImgUrl()).isEqualTo(foundWork.getWorkImgUrl());
	}

}
