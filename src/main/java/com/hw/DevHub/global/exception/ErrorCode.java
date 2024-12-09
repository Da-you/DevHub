package com.hw.DevHub.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    PASSWORD_MISS_MATCH(HttpStatus.BAD_REQUEST, "40001", "비밀번호를 다시 확인해 주세요."),
    ALREADY_RELATIONSHIP(HttpStatus.BAD_REQUEST, "40002", "이미 팔로우한 유저입니다."),
    UNABLE_TO_SELF_REQUEST(HttpStatus.BAD_REQUEST, "40003", "자기 자신에게 요청할 수 없습니다."),

    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "40401", "등록되지 않은 이메일 입니다."),
    RELATIONSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "40402", "팔로우한 대상이 아닙니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "40403", "존재하지 않는 사용자 입니다."),
    ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "40404", "알람이 존재하지 않습니다."),

    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "40301", "중복된 이메일 입니다."),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "40302", "중복된 닉네임 입니다."),
    DUPLICATED_PHONE_NUMBER(HttpStatus.CONFLICT, "40303", "중복된 연락처 입니다."),

    UNAUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "40101", "로그인 후 이용 가능합니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "40102", "해당 요청에 대한 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}