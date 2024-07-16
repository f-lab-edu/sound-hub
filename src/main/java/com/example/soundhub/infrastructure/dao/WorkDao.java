package com.example.soundhub.infrastructure.dao;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;

import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.config.exception.DatabaseException;
import com.example.soundhub.domain.Work;
import com.example.soundhub.infrastructure.mapper.WorkMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WorkDao {

	private final WorkMapper workMapper;

	public Work create(Work work) {
		try {
			int success = workMapper.create(work);
			if (success == 0) {
				log.error("No work was created, work details: {}", work);
				throw new DatabaseException(DB_INSERT_ERROR);
			}
		} catch (DuplicateKeyException e) {
			log.error("Duplicate Key Insert ERROR! {}", e.getMessage());
			log.error("Params : {}", work);
			throw new BadRequestException(DUPLICATE_ERROR);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("insertWork ERROR! {}", e.getMessage());
			log.error("Params : {}", work);
			throw new DatabaseException(DATABASE_ERROR);
		}

		return findById(work.getId());
	}

	public Work findById(Long workId) {
		try {
			Work work = workMapper.findById(workId);
			if (work == null) {
				throw new BadRequestException(NOT_FOUND_ERROR);
			}
			return work;
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}

	public List<Work> findAllWorksByUserId(Long userId) {
		try {
			List<Work> works = workMapper.findAllWorksByUserId(userId);
			if (works == null) {
				throw new BadRequestException(NOT_FOUND_ERROR);
			}
			return works;
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}

	public void deleteWork(Long workId) {
		try {
			workMapper.deleteWork(workId);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}

	public void updateNumberOfPlays(Work work) {
		try {
			int success = workMapper.updateNumberOfPlays(work.getId(), work.getNumberOfPlays());
			if (success == 0) {
				log.error("update number of plays is failed : {}", work);
				throw new DatabaseException(DB_UPDATE_ERROR);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}

	public void updateLikes(Work work) {
		try {
			int success = workMapper.updateLikes(work.getId(), work.getLikes());
			if (success == 0) {
				log.error("update number of plays is failed : {}", work);
				throw new DatabaseException(DB_UPDATE_ERROR);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}
}
