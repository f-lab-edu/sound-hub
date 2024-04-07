package com.example.soundhub.infrastructure.mapper;

import com.example.soundhub.domain.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findUserById(Long id);

    int register(User user);

    @Select("SELECT EXISTS(SELECT 1 FROM Users WHERE login_id = #{login_id})")
    boolean existsByLoginId(String login_id);
}

