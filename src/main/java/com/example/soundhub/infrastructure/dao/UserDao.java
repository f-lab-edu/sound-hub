package com.example.soundhub.infrastructure.dao;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;

import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.config.exception.DatabaseException;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserDao {

	private final UserMapper userMapper;

	@Autowired
	public UserDao(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public User findByLoginId(String loginId) {
		try {
			return userMapper.findUserByLoginId(loginId);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_USER);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("A data access error occurred: {}", e.getMessage());
			throw new DatabaseException(DATABASE_ERROR);
		}
	}

	public String create(User user) {
		try {
			userMapper.create(user);
		} catch (DuplicateKeyException e) {
			log.error("Duplicate Key Insert ERROR! {}", e.getMessage());
			log.error("Params : {}", user);
			throw new BadRequestException(DUPLICATE_ERROR);
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException(NOT_FOUND_USER);
		} catch (QueryTimeoutException e) {
			throw new DatabaseException(QUERY_TIMEOUT_ERROR);
		} catch (DataAccessException e) {
			log.error("insertMember ERROR! {}", e.getMessage());
			log.error("Params : {}", user);
			throw new DatabaseException(DATABASE_ERROR);
		}
		return user.getName();
	}
}