package com.hw.DevHub.domain.users.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String nickname;
    private String profileMessage;
    private String profileImagePath;
    private LocalDateTime createAt;

    public User(String email, String password, String name, String phoneNumber, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }

    public static class Builder {

        private String email;
        private String password;
        private String name;
        private String phoneNumber;
        private String nickname;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            // 여기서 this 키워드는 builder 객체 자신을 의미 -> 빌더 객체 자신을 리턴함으로써 메서드 호출 후 연속적으로 빌더 메서드를 체이닝하여 호출 ex) new User.Builder().email(값).password(값)
            return this;
        }

        public User build() {
            return new User(email, password, name, phoneNumber, nickname);
        }
    }


}
