package com.example.soundhub.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.soundhub.domain.User;
import com.example.soundhub.domain.Work;
import com.example.soundhub.domain.WorkLike;
import com.example.soundhub.infrastructure.dao.UserDao;
import com.example.soundhub.infrastructure.dao.WorkDao;
import com.example.soundhub.infrastructure.dao.WorkLikeDao;
import com.example.soundhub.presentation.dto.request.WorkRequest;
import com.example.soundhub.presentation.dto.response.WorkResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkService {
	private final WorkDao workDao;

	private final UserDao userDao;

	private final WorkLikeDao workLikeDao;

	private final S3Service s3Service;

	@Transactional
	public Long addWork(WorkRequest.addWork request, Long userId, MultipartFile image) {
		User user = userDao.findById(userId);

		String imgUrl = s3Service.upload(image, "images");

		Work work = Work.builder()
			.userId(user.getId())
			.name(request.getName())
			.workType(request.getWorkType())
			.youtubeUrl(request.getYoutubeUrl())
			.workImgUrl(imgUrl)
			.workCreatedDate(request.getWorkCreatedDate())
			.likes(0L)
			.numberOfPlays(0L)
			.build();

		workDao.create(work);

		return work.getId();
	}

	@Transactional
	public List<WorkResponse.getWorksInfo> viewUserWorks(Long userId) {
		List<Work> works = workDao.findAllWorksByUserId(userId);

		return works.stream().map(WorkResponse.getWorksInfo::toDomain).toList();
	}

	@Transactional
	public void deleteWork(Long workId) {
		workDao.deleteWork(workId);
	}

	@Transactional
	public String playUserWork(Long workId) {
		Work work = workDao.findById(workId);
		work.incrementPlays();

		workDao.updateNumberOfPlays(work);

		return work.getYoutubeUrl();
	}

	@Transactional
	public boolean likeUserWork(Long workId, Long userId) {
		Work work = workDao.findById(workId);

		if (workLikeDao.doesWorkLikeExistForUser(userId, work.getId())) {
			workLikeDao.deleteByUserIdAndWorkId(userId, work.getId());

			work.decrementLikes();
			workDao.updateLikes(work);

			return false;
		} else {
			WorkLike workLike = WorkLike.builder().workId(workId).userId(userId).build();
			workLikeDao.create(workLike);

			work.incrementLikes();
			workDao.updateLikes(work);

			return true;
		}
	}
}
