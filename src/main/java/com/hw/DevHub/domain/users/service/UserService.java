package com.hw.DevHub.domain.users.service;

import com.hw.DevHub.domain.users.component.encryption.CustomEncryptionComponent;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.domain.users.dto.UserRequest.SignUpRequest;
import com.hw.DevHub.domain.users.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final CustomEncryptionComponent encryptionComponent;

    @Transactional
    public String addUser(SignUpRequest request) {

        String encodedPassword = encryptionComponent.encryptPassword(request.getEmail(),
            request.getPassword());

        User user = new User.Builder()
            .email(request.getEmail())
            .password(encodedPassword)
            .name(request.getName())
            .phoneNumber(request.getPhoneNumber())
            .nickname(request.getNickname())
            .build();
        userMapper.insertUser(user);
        return "유저 생성";
    }

    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        if (!userMapper.isEmailDuplicated(email)) {
            return true;
        } else {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        if (!userMapper.isNicknameDuplicated(nickname)) {
            return true;
        } else {
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }
    }

    @Transactional(readOnly = true)
    public boolean checkPhoneNumber(String phoneNumber) {
        if (!userMapper.isPhoneNumberDuplicated(phoneNumber)) {
            return true;
        } else {
            throw new IllegalArgumentException("이미 존재하는 연락처 입니다.");
        }
    }
}
