package com.example.soundhub.infrastructure.dao;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;

import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.config.exception.DatabaseException;
import com.example.soundhub.domain.WorkLike;
import com.example.soundhub.infrastructure.mapper.WorkLikeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WorkLikeDao {

	private final WorkLikeMapper workLikeMapper;

	public WorkLike create(WorkLike worklike) {
		try {
			int success = workLikeMapper.create(worklike);
			if (success == 0) {
				log.error("No work was created, work details: {}", worklike);
				throw new DatabaseException(DB_INSERT_ERROR);
			}
		} catch (DuplicateKeyException e) {
			log.error("Duplicate Key Insert ERROR! {}", e.getMessage());
			log.error("Params : {}", worklike);
			throw new BadRequestException(DUPLICATE_ERROR);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("insertWork ERROR! {}", e.getMessage());
			log.error("Params : {}", worklike);
			throw new DatabaseException(DATABASE_ERROR);
		}

		return findById(worklike.getId());
	}

	public WorkLike findById(Long workLikeId) {
		try {
			WorkLike workLike = workLikeMapper.findById(workLikeId);
			if (workLike == null) {
				throw new BadRequestException(NOT_FOUND_ERROR);
			}
			return workLike;
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}


	public boolean doesWorkLikeExistForUser(Long userId, Long workId) {
		try {
			return workLikeMapper.doesWorkLikeExistForUser(userId, workId);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}

	public void deleteByUserIdAndWorkId(Long userId, Long workId) {
		try {
			int success = workLikeMapper.deleteByUserIdAndWorkId(userId, workId);
			if (success == 0) {
				log.error("No work like found to delete for user_id: {} and work_id: {}", userId, workId);
				throw new BadRequestException(NOT_FOUND_ERROR);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("deleteWorkLike ERROR! {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}

}
