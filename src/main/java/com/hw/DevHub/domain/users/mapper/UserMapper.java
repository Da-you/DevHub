package com.hw.DevHub.domain.users.mapper;

import com.hw.DevHub.domain.users.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Mapper 해당 애노테이션이 적용된 인터페이스를 MyBatis Mapper로 등록
 */
@Mapper
public interface UserMapper {

    void insertUser(User user);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByPhoneNumber(String phoneNumber);

    User findByEmailAndPassword(String email, String password);

    User findByUserId(Long userId);
}
