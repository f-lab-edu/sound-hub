package com.example.soundhub.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import com.example.soundhub.application.service.S3Service;
import com.example.soundhub.application.service.WorkService;
import com.example.soundhub.domain.User;
import com.example.soundhub.domain.Work;
import com.example.soundhub.infrastructure.dao.UserDao;
import com.example.soundhub.infrastructure.dao.WorkDao;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import com.example.soundhub.jwt.JwtUtil;
import com.example.soundhub.presentation.dto.request.WorkRequest;

@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
public class WorkServiceTest {

	@Mock
	private UserMapper userMapper;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private WorkDao workDao;

	@Mock
	private UserDao userDao;

	@Mock
	private S3Service s3Service;

	@InjectMocks
	private WorkService workService;

	@Test
	public void testAddWork_Success() {
		// Given
		WorkRequest.addWork request = WorkRequest.addWork.builder()
			.name("userName")
			.workType("ALBUM")
			.youtubeUrl("www.youtube.com")
			.build();

		User user = User.builder()
			.name("JohnDoe")
			.loginId("johndoe123")
			.password("password123")
			.build();

		MockMultipartFile file = new MockMultipartFile(
			"썸네일 이미지",
			"thumbnail.png",
			MediaType.IMAGE_PNG_VALUE,
			"thumbnail".getBytes()
		);

		Work work = Work.builder()
			.userId(user.getId())
			.name(request.getName())
			.workType(request.getWorkType())
			.youtubeUrl(request.getYoutubeUrl())
			.build();

		// When
		when(userDao.findById(user.getId())).thenReturn(user);
		when(workDao.create(work)).thenReturn(work);

		// Then
		assertThat(workService.addWork(request, user.getId(), file)).isEqualTo(work.getId());
	}

}