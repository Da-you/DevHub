package com.hw.devhub.global.config;

import lombok.RequiredArgsConstructor;

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
