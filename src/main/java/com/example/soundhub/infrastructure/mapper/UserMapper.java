package com.example.soundhub.infrastructure.mapper;

import com.example.soundhub.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findUserById(Long id);

    @Select("SELECT * FROM users WHERE login_id = #{loginId}")
    User findUserByLoginId(String loginId);

    int create(User user);

    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE login_id = #{loginId})")
    boolean existsByLoginId(String loginId);
}

