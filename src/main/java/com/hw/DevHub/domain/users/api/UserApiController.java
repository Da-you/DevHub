package com.hw.DevHub.domain.users.api;

import com.hw.DevHub.domain.users.dto.UserRequest.SignUpRequest;
import com.hw.DevHub.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping
    public String addUser(@RequestBody @Validated SignUpRequest request) {
        userService.addUser(request);
        return "success";
    }

    @GetMapping("/check-email")
    private boolean checkEmail(@RequestParam("email") String email) {
        return userService.checkEmail(email);
    }
    @GetMapping("/check-nickname")
    private boolean checkNickname(@RequestParam("nickname") String nickname) {
        return userService.checkNickname(nickname);
    }
    @GetMapping("/check-phone-number")
    private boolean checkPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return userService.checkPhoneNumber(phoneNumber);
    }


}
