package com.hw.devhub.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JPA 환경설정을 구성
 */
@Configuration
public class JpaQuerydslConfig {


    // DB 사용을 위한 주요 인터페이스 주입
    @PersistenceContext
    private EntityManager em;

    /**
     * JPAQueryFactory 를 bean으로 설정해 매번 EntityManager를 주입받는 부분을 줄임
     *
     * @return JPAQueryFactory
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

}
