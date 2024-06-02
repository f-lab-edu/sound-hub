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
		log.debug("Starting create method for profile: {}", profile);  // 메소드 시작 로그
		try {
			int success = profileMapper.create(profile);
			log.debug("Insert operation returned with success status: {}", success);  // 삽입 성공 여부 로그

			if (success == 0) { // Assuming 'create' returns the number of inserted rows.
				log.error("No profile was created, profile details: {}", profile);
				throw new DatabaseException(DB_INSERT_ERROR);
			}
			return findById(profile.getId());
		} catch (DuplicateKeyException e) {
			log.error("Duplicate Key Insert ERROR! {}", e.getMessage());
			log.error("Params : {}", profile);
			throw new BadRequestException(DUPLICATE_ERROR);
		} catch (EmptyResultDataAccessException e) {
			log.error("No result found where expected: {}", e.getMessage());
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			log.error("Query timed out: {}", e.getMessage());
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("Data access exception occurred: {}", e.getMessage());
			log.error("Params : {}", profile);
			throw new DatabaseException(DATABASE_ERROR);
		} catch (Exception e) {
			log.error("An unexpected error occurred: {}", e.getMessage());
			log.error("Error with profile: {}", profile);
			throw e;  // Re-throw to let it be handled by the global exception handler
		}
	}

	public Profile update(Profile profile) {
		log.debug("Starting update method for profile: {}", profile);
		try {
			int success = profileMapper.update(profile);
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
			Throwable cause = e.getCause();
			String causeMessage = cause != null ? cause.getMessage() : "No cause available";
			log.error("insertWork ERROR! {}", causeMessage);
			log.error("Params : {}", profile.toString());
			throw new DatabaseException(DATABASE_ERROR);
		}

		return findByUserId(profile.getUserId());
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

	public Profile findByUserId(Long userId) {
		try {
			return profileMapper.findByUserId(userId);
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

