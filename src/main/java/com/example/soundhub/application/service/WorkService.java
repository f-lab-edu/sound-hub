package com.example.soundhub.application.service;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.soundhub.config.exception.AwsS3Exception;
import com.example.soundhub.domain.User;
import com.example.soundhub.domain.Work;
import com.example.soundhub.infrastructure.dao.UserDao;
import com.example.soundhub.infrastructure.dao.WorkDao;
import com.example.soundhub.jwt.JwtUtil;
import com.example.soundhub.presentation.dto.request.WorkRequest;
import com.example.soundhub.presentation.dto.response.WorkResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkService {

	private final JwtUtil jwtUtil;

	private final WorkDao workDao;

	private final UserDao userDao;

	private final S3Service s3Service;

	@Transactional
	public Long addWork(WorkRequest.addWork request, Long userId, MultipartFile image) {
		User user = userDao.findById(userId);

		String imgUrl = null;
		if (image.getSize() > 0) {
			try {
				imgUrl = s3Service.upload(image, "images");
			} catch (IOException e) {
				throw new AwsS3Exception(IMAGE_UPLOAD_ERROR);
			}
		}

		Work work = Work.builder()
			.userId(user.getId())
			.name(request.getName())
			.workType(request.getWorkType())
			.youtubeUrl(request.getYoutubeUrl())
			.workImgUrl(imgUrl)
			.workCreatedDate(request.getWorkCreatedDate())
			.build();

		workDao.create(work);

		return work.getId();
	}

	@Transactional
	public List<WorkResponse.getWorksInfo> viewUserWorks(Long userId) {
		List<Work> works = workDao.findAllWorksByUserId(userId);

		return works.stream()
			.map(WorkResponse.getWorksInfo::toDomain)
			.toList();
	}

	@Transactional
	public void deleteWork(Long workId) {
		workDao.deleteWork(workId);
	}
}
