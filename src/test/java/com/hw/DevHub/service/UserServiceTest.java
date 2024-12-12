package com.hw.DevHub.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hw.DevHub.domain.users.component.encryption.CustomEncryptionComponent;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.domain.users.dto.UserRequest.SignUpRequest;
import com.hw.DevHub.domain.users.mapper.UserMapper;
import com.hw.DevHub.domain.users.service.UserService;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import org.apache.catalina.authenticator.SavedRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @ExtendWith : Junit5의 확장 어노테이션을 사용할 수 있다.
 * @Mock : mock 객체를 생성한다.
 * @InjectMock : @Mock이 붙은 객체를 @InjectMock이 붙은 객체에 주입시킬 수 있다.
 */

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;
    @Mock
    CustomEncryptionComponent encryptionComponent;

    User testUser;

    User encryptedUser;

    SignUpRequest signUpRequestSetUp;

    @BeforeEach
    public void setUp() {
        testUser = new User.Builder()
            .email("joe@test.com")
            .password("abcd1234")
            .name("JOE")
            .phoneNumber("01012345678")
            .nickname("doe")
            .build();

        encryptedUser = new User.Builder().email("joe@test.com")
            .password(encryptionComponent.encryptPassword("joe@test.com", "abcd1234"))
            .name("JOE")
            .phoneNumber("01012345678")
            .nickname("doe")
            .build();

        signUpRequestSetUp = SignUpRequest.builder().email("joe@test.com")
            .password("abcd1234")
            .name("JOE")
            .phoneNumber("01012345670")
            .nickname("doe").build();
    }

    // 마이페이지 조회
    // 닉네임 중복
    // 연락처 중복

    @Test
    @DisplayName("이메일 중복 시 예외가 발생하며 회원가입에 실패한다.")
    void signUpWithDuplicateEmail() {
        //given
        String inputEmail = signUpRequestSetUp.getEmail();
        //when
        when(userMapper.existsByEmail(inputEmail)).thenReturn(false);
        //then
        Assertions.assertThatThrownBy(() -> userService.checkEmail(inputEmail))
            .isInstanceOf(GlobalException.class);

        //verify
        verify(userMapper, times(1)).existsByEmail(inputEmail);
    }

    @Test
    @DisplayName("회원 가입 성공")
    public void signUpWithSuccess() {

        String encodedPassword = encryptionComponent.encryptPassword("joe@test.com", "abcd1234");

        when(encryptionComponent.encryptPassword(signUpRequestSetUp.getEmail(),
            signUpRequestSetUp.getPassword()))
            .thenReturn(encodedPassword);
        doNothing().when(userMapper).insertUser(any(SignUpRequest.class));

        userService.addUser(signUpRequestSetUp);

        verify(userMapper).insertUser(any(SignUpRequest.class));
    }
}
