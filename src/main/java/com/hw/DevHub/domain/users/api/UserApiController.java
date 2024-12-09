package com.hw.DevHub.domain.users.api;

import com.hw.DevHub.domain.users.dto.UserRequest.LoginRequest;
import com.hw.DevHub.domain.users.dto.UserRequest.SignUpRequest;
import com.hw.DevHub.domain.users.dto.UserResponse.MypageResponse;
import com.hw.DevHub.domain.users.service.SessionLoginService;
import com.hw.DevHub.domain.users.service.UserService;
import com.hw.DevHub.global.annotation.CurrentUser;
import com.hw.DevHub.global.annotation.LoginRequired;
import com.hw.DevHub.global.response.ApiResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;
    private final SessionLoginService loginService;

    @PostMapping
    public void addUser(@RequestBody @Validated SignUpRequest request) {
        userService.addUser(request);
    }

    @GetMapping("/check-email")
    private ApiResponse<Boolean> checkEmail(@RequestParam("email") String email) {
        return ApiResponse.success(userService.checkEmail(email));
    }

    @GetMapping("/check-nickname")
    private ApiResponse<Boolean> checkNickname(@RequestParam("nickname") String nickname) {
        return ApiResponse.success(userService.checkNickname(nickname));
    }

    @GetMapping("/check-phone-number")
    private ApiResponse<Boolean> checkPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return ApiResponse.success(userService.checkPhoneNumber(phoneNumber));
    }

    @PostMapping("/login")
    public void login(@RequestBody @Validated LoginRequest request) {
        loginService.login(request);
    }

    @DeleteMapping("/logout")
    public void logout(@CurrentUser Long userId) {
        loginService.logout();
    }

    @LoginRequired
    @GetMapping("/mypage/{targetId}")
    public ApiResponse<MypageResponse> getMypage(@CurrentUser Long userId,
        @PathVariable Long targetId) {
        return ApiResponse.success(userService.getMypage(userId, targetId));
    }

    @LoginRequired
    @PatchMapping("/profile-message")
    public void updateProfileMessage(@CurrentUser Long userId,
        @RequestParam @NotNull String profileMessage) {
        userService.updateProfileMessage(userId, profileMessage);
    }

    @LoginRequired
    @PatchMapping("/profile-image-path")
    public void updateProfileImagePath(@CurrentUser Long userId, @RequestPart MultipartFile file) {
        userService.updateProfileImagePath(userId, file);
    }


}
