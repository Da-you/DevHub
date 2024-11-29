package com.hw.DevHub.domain.users.api;

import com.hw.DevHub.domain.users.service.FollowService;
import com.hw.DevHub.global.annotation.CurrentUser;
import com.hw.DevHub.global.annotation.LoginRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowApiController {

    private final FollowService followService;

    @PostMapping
    @LoginRequired
    public void followUser(@CurrentUser Long userId,
        @RequestParam(name = "targetId") Long targetId) {
        followService.followUser(userId, targetId);
    }

    @DeleteMapping
    @LoginRequired
    public void removeFollow(@CurrentUser Long userId,
        @RequestParam(name = "targetId") Long targetId) {
        followService.unfollowUser(userId, targetId);
    }
}
