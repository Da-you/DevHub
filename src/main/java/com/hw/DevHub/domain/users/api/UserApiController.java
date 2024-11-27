package com.hw.DevHub.domain.users.api;

import com.hw.DevHub.domain.users.dto.UserRequest.LoginRequest;
import com.hw.DevHub.domain.users.dto.UserRequest.SignUpRequest;
import com.hw.DevHub.domain.users.service.SessionLoginService;
import com.hw.DevHub.domain.users.service.UserService;
import com.hw.DevHub.global.annotation.CurrentUser;
import com.hw.DevHub.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
