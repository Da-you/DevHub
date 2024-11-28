package com.hw.DevHub.domain.feed.api;

import com.hw.DevHub.domain.feed.dto.FeedRequest;
import com.hw.DevHub.domain.feed.dto.FeedRequest.PostFeedRequest;
import com.hw.DevHub.domain.feed.dto.FeedResponse.ViewFeed;
import com.hw.DevHub.domain.feed.service.FeedService;
import com.hw.DevHub.global.annotation.CurrentUser;
import com.hw.DevHub.global.annotation.LoginRequired;
import com.hw.DevHub.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedApiController {


    private final FeedService feedService;

    @PostMapping
    @LoginRequired
    public void postFeed(@CurrentUser Long userId, @RequestBody PostFeedRequest request) {
        feedService.postFeed(userId, request);
    }

    @GetMapping("/{feedId}")
    @LoginRequired
    public ApiResponse<ViewFeed> getFeed(@CurrentUser Long userId,
        @PathVariable("feedId") Long feedId) {
        return ApiResponse.success(feedService.getFeedById(userId, feedId));
    }

    @GetMapping
    @LoginRequired
    public ApiResponse<List<ViewFeed>> getFeeds(@CurrentUser Long userId) {
        return ApiResponse.success(feedService.getFeeds(userId));
    }

    @LoginRequired
    @PatchMapping("/{feedId}")
    public void updateFeed(@CurrentUser Long userId, @PathVariable("feedId") Long feedId,
        @RequestBody PostFeedRequest request) {
        feedService.updateFeed(userId, feedId, request);
    }

    @LoginRequired
    @DeleteMapping("/{feedId}")
    public void deleteFeed(@CurrentUser Long userId, @PathVariable("feedId") Long feedId) {
        feedService.deleteFeed(userId,feedId);
    }

}
