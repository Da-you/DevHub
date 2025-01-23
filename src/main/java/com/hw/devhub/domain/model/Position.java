package com.hw.devhub.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Position {
    BE("백엔드"),
    FE("프론트엔드"),
    DE("디자이너"),
    PM("프로덕트 기획"),
    DEV_OPS("데브옵스"),
    AI("AI"),
    FULL_STACK("풀스택"),
    DATA("데이터 엔지니어"),
    ML("머신러닝"),
    APP("앱 개발자");

    public final String name;

}
