package com.hw.DevHub.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hw.DevHub.domain.users.component.encryption.CustomEncryptionComponent;
import com.hw.DevHub.domain.users.dto.UserRequest.SignUpRequest;
import com.hw.DevHub.domain.users.service.UserService;
import com.hw.DevHub.global.exception.GlobalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    SignUpRequest signUpRequestSetUp;

    @BeforeEach
    public void setUp() {

        signUpRequestSetUp = SignUpRequest.builder().email("joe@test.com")
            .password("abcd1234")
            .name("JOE")
            .phoneNumber("01012345670")
            .nickname("doe").build();
    }
    // 닉네임 중복
    @Test
    @DisplayName("닉네임 중복 시 예외가 발생")
    void signUpWithDuplicateNickname() {
        //given
        String nickname = signUpRequestSetUp.getNickname();
        //when
        when(userMapper.existsByNickname(nickname)).thenReturn(true);
        //then
        assertThatThrownBy(() -> userService.checkNickname(nickname))
            .isInstanceOf(GlobalException.class);
    }

    // 연락처 중복
    @Test
    @DisplayName("연락처 중복 시 예외가 발생")
    void signUpWithDuplicatePhoneNumber() {
        //given
        String phoneNumber = signUpRequestSetUp.getPhoneNumber();
        //when
        when(userMapper.existsByPhoneNumber(phoneNumber)).thenReturn(true);
        //then
        assertThatThrownBy(() -> userService.checkPhoneNumber(phoneNumber))
            .isInstanceOf(GlobalException.class);
    }

    @Test
    @DisplayName("이메일 중복 시 예외가 발생")
    void signUpWithDuplicateEmail() {
        //given
        String inputEmail = signUpRequestSetUp.getEmail();
        //when
        when(userMapper.existsByEmail(inputEmail)).thenReturn(true);
        //then
        assertThatThrownBy(() -> userService.checkEmail(inputEmail))
            .isInstanceOf(GlobalException.class);

        //verify
        verify(userMapper, times(1)).existsByEmail(inputEmail);
    }

//    @Test
//    @DisplayName("회원 가입 성공")
//    public void signUpWithSuccess() {
//
//        String encodedPassword = encryptionComponent.encryptPassword("joe@test.com", "abcd1234");
//
//        when(encryptionComponent.encryptPassword(signUpRequestSetUp.getEmail(),
//            signUpRequestSetUp.getPassword()))
//            .thenReturn(encodedPassword);
//        doNothing().when(userMapper).insertUser(any(SignUpRequest.class));
//
//        userService.addUser(signUpRequestSetUp);
//
//        verify(userMapper, times(1)).insertUser(any(SignUpRequest.class));
//    }
}
