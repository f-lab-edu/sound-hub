package com.example.soundhub.infrastructure.dao;

import com.example.soundhub.config.exception.BadRequestException;
import com.example.soundhub.config.exception.DatabaseException;
import com.example.soundhub.domain.User;
import com.example.soundhub.infrastructure.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

@Repository
@Slf4j
public class UserDao {
    private UserMapper userMapper;

    public User findByLoginId(String loginId) {
        try {
            return userMapper.findUserByLoginId(loginId);
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequestException(NOT_FOUND_USER);
        } catch (QueryTimeoutException e) {
            throw new DatabaseException(QUERY_TIMEOUT_ERROR);
        } catch (DataAccessException e) {
            log.error("A data access error occurred: " , e.getMessage());
            throw new DatabaseException(DATABASE_ERROR);
        }
    }
}