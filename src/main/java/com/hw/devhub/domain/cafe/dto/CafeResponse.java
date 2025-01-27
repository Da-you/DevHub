package com.hw.devhub.domain.cafe.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CafeResponse {
	private Long cafeId;
	private String name;
	private String roadAddress;
	private String zipCode; // 신 우편번호
	private double longitude; // 경도
	private double latitude; // 위도

	@QueryProjection
	public CafeResponse(Long cafeId, String name, String roadAddress, String zipCode, double longitude,
		double latitude) {
		this.cafeId = cafeId;
		this.name = name;
		this.roadAddress = roadAddress;
		this.zipCode = zipCode;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
