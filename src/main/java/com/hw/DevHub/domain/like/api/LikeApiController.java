package com.hw.DevHub.domain.like.api;

import com.hw.DevHub.domain.like.service.LikeService;
import com.hw.DevHub.global.annotation.CurrentUser;
import com.hw.DevHub.global.annotation.LoginRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeApiController {

    private final LikeService likeService;

    @LoginRequired
    @PostMapping("/{feedId}")
    public void feedLike(@CurrentUser Long userId, @PathVariable Long feedId) {
        likeService.feedLike(userId, feedId);
    }

}
