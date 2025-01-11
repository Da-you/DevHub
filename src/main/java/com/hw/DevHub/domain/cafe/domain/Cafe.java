package com.hw.DevHub.domain.cafe.domain;

import com.hw.DevHub.domain.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 서울-경기 카페 검색
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cafe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String roadNamedAddress; // 도로명 주소 + 건물명 + 층정보 + 호 정보
    private String zipCode; // 신 우편번호
    private double longitude; // 경도
    private double latitude; // 위도


    @Builder
    public Cafe(String name, String roadNamedAddress, String zipCode, double latitude,
        double longitude) {
        this.name = name;
        this.roadNamedAddress = roadNamedAddress;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Cafe{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", roadNamedAddress='" + roadNamedAddress + '\'' +
            ", zipCode='" + zipCode + '\'' +
            ", longitude=" + longitude +
            ", latitude=" + latitude +
            '}';
    }
}
