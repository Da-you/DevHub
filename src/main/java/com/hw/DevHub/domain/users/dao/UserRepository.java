package com.hw.DevHub.domain.users.dao;

import com.hw.DevHub.domain.users.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {



    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsById(Long userId);


}
