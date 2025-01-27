package com.hw.devhub.domain.cafe.dao;

import static com.hw.devhub.domain.cafe.domain.QCafe.*;

import com.hw.devhub.domain.cafe.domain.QCafe;
import com.hw.devhub.domain.cafe.dto.CafeResponse;
import com.hw.devhub.domain.cafe.dto.QCafeResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class CafeQueryRepository {

	private final JPAQueryFactory queryFactory;

	public List<CafeResponse> searchCafe(String keyword) {
		QCafe cafe = QCafe.cafe;
		return queryFactory.select(new QCafeResponse(
				cafe.id,
				cafe.name,
				cafe.roadNamedAddress,
				cafe.zipCode,
				cafe.longitude,
				cafe.latitude
			))
			.from(cafe)
			.where(cafeNameLike(keyword)
				.or(cafeAddressLike(keyword)))
			.fetch();
	}


	private BooleanExpression cafeNameLike(String name) {
		return StringUtils.hasText(name) ? cafe.name.contains(name) : null;
	}

	private BooleanExpression cafeAddressLike(String address) {
		return StringUtils.hasText(address) ? cafe.roadNamedAddress.contains(address) : null;
	}
}
