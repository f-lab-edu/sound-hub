package com.example.soundhub.infrastructure.dao;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;

import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.config.exception.DatabaseException;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserDao {

	private final UserMapper userMapper;

	public User findByLoginId(String loginId) {
		try {
			return userMapper.findUserByLoginId(loginId);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}

	public User findById(Long userId) {
		try {
			return userMapper.findUserById(userId);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}

	public User create(User user) {
		try {
			int success = userMapper.create(user);
			if (success == 0) { // Assuming 'create' returns the number of inserted rows.
				log.error("No user was created, user details: {}", user);
				throw new DatabaseException(DB_INSERT_ERROR);
			}
		} catch (DuplicateKeyException e) {
			log.error("Duplicate Key Insert ERROR! {}", e.getMessage());
			log.error("Params : {}", user);
			throw new BadRequestException(DUPLICATE_ERROR);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_ERROR);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("insertMember ERROR! {}", e.getMessage());
			log.error("Params : {}", user);
			throw new DatabaseException(DATABASE_ERROR);
		}

		return findById(user.getId());
	}
}