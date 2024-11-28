package com.hw.DevHub.domain.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequest {

        @Email
        @NotBlank(message = "email은 필수 입력값 입니다.")
        private String email;
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "^[a-z0-9]{8,20}$", message = "소문자와 숫자(0~9)를 포함하여 8 ~ 20자의 문자열을 입력해주세요")
        private String password;
        @NotBlank(message = "이름은 필수 입력값 입니다.")
        private String name;
        @Pattern(regexp = "^01[016789]\\d{7,8}$", message = "유효한 핸드폰 번호를 입력해주세요 (예: 01012345678)")
        @NotBlank(message = "연락처는 필수 입력값 입니다.")
        private String phoneNumber;
        @NotBlank(message = "닉네임은 필수 입력값 입니다.")
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {

        @Email
        @NotBlank(message = "email은 필수 입력값 입니다.")
        private String email;
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
    }

}
