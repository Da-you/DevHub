package com.hw.DevHub.domain.cafe.dao;

import static com.hw.DevHub.domain.cafe.domain.QCafe.cafe;

import com.hw.DevHub.domain.cafe.domain.Cafe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CafeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Cafe> searchCafe(String keyword) {
        return queryFactory.select(cafe).from(cafe).where(cafe.name.like("%" + keyword + "%"))
            .fetch();
    }
}
