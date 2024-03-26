package com.example.soundhub.infrastructure.mapper;

import com.example.soundhub.domain.model.User;
import com.example.soundhub.infrastructure.model.UserRow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    // @Select(SELECT * FROM person WHERE id = #{id}") 으로 작성 가능. (보통 복잡한 쿼리만 xml 작성)
    UserRow find(Long id);

    int create(User user);

}

