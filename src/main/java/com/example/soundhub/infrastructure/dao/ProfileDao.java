package com.example.soundhub.infrastructure.dao;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;

import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.config.exception.DatabaseException;
import com.example.soundhub.domain.Profile;
import com.example.soundhub.infrastructure.mapper.ProfileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProfileDao {

	private final ProfileMapper profileMapper;

	public Profile create(Profile profile) {
		try {
			int success = profileMapper.create(profile);
			if (success == 0) { // Assuming 'create' returns the number of inserted rows.
				log.error("No profile was created, profile details: {}", profile);
				throw new DatabaseException(DB_INSERT_ERROR);
			}
		} catch (DuplicateKeyException e) {
			log.error("Duplicate Key Insert ERROR! {}", e.getMessage());
			log.error("Params : {}", profile);
			throw new BadRequestException(DUPLICATE_ERROR);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("insertWork ERROR! {}", e.getMessage());
			log.error("Params : {}", profile);
			throw new DatabaseException(DATABASE_ERROR);
		}

		return findById(profile.getId());
	}

	public Profile findById(Long profileId) {
		try {
			return profileMapper.findById(profileId);
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

