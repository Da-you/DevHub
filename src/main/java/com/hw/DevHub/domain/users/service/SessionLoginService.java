package com.hw.DevHub.domain.users.service;

import static com.hw.DevHub.global.constant.SessionConst.LOGIN_USER;

import com.hw.DevHub.domain.users.component.encryption.CustomEncryptionComponent;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.domain.users.dto.UserRequest.LoginRequest;
import com.hw.DevHub.domain.users.mapper.UserMapper;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SessionLoginService {

    private final UserMapper userMapper;
    private final CustomEncryptionComponent encryptionComponent;
    /**
     * 서블릿이 제공하는 기술 서블릿을 통해 session 생성시 JSESSIONID 이라는 이름의 쿠키를 생성 추정 불가한 랜덤값을 넣어준다.
     */
    private final HttpSession httpSession;

    @Transactional
    public void login(LoginRequest request) {
        if (userMapper.existsByEmail(request.getEmail())) {
            throw new GlobalException(ErrorCode.EMAIL_NOT_FOUND);
        }
        String decodePassword = encryptionComponent.encryptPassword(request.getEmail(),
            request.getPassword());
        User user = userMapper.findByEmailAndPassword(request.getEmail(), decodePassword);
        if (user == null) {
            throw new GlobalException(ErrorCode.PASSWORD_MISS_MATCH);
        }
        httpSession.setAttribute(LOGIN_USER, user.getUserId());
    }

    @Transactional
    public void logout() {
        httpSession.invalidate();
    }

    public Long getCurrentUserId() {
        return (Long) httpSession.getAttribute(LOGIN_USER);
    }

}
