package com.hw.DevHub.global.config;

import com.hw.DevHub.domain.users.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {

    // property에 따른 빈주입
//    @Bean
//    @ConditionalOnProperty(value = "app.repository.type", havingValue = "jpa", matchIfMissing = true)
//    public UserRepository userRepository(UserJPARepository userJPARepository) {
//        return new UserRepositoryAdapter(userJPARepository);
//    }
}
