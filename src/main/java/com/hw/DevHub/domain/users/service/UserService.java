package com.hw.DevHub.domain.users.service;

import com.hw.DevHub.domain.users.component.encryption.CustomEncryptionComponent;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.domain.users.dto.UserRequest.SignUpRequest;
import com.hw.DevHub.domain.users.mapper.UserMapper;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final CustomEncryptionComponent encryptionComponent;

    @Transactional
    public void addUser(SignUpRequest request) {

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
    }

    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        if (userMapper.existsByEmail(email)) {
            return true;
        } else {
            throw new GlobalException(ErrorCode.DUPLICATED_EMAIL);
        }

    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        if (!userMapper.existsByNickname(nickname)) {
            return true;
        } else {
            throw new GlobalException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }

    @Transactional(readOnly = true)
    public boolean checkPhoneNumber(String phoneNumber) {
        if (!userMapper.existsByPhoneNumber(phoneNumber)) {
            return true;
        } else {
            throw new GlobalException(ErrorCode.DUPLICATED_PHONE_NUMBER);
        }
    }
}
