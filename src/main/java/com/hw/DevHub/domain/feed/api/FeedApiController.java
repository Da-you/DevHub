package com.hw.DevHub.domain.feed.api;

import com.hw.DevHub.domain.comment.dto.CommentRequest;
import com.hw.DevHub.domain.comment.service.CommentService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedApiController {


    private final FeedService feedService;
    private final CommentService commentService;

    @PostMapping
    @LoginRequired
    public void postFeed(@CurrentUser Long userId,
        @RequestParam(name = "content") PostFeedRequest request,
        @RequestPart List<MultipartFile> images) {
        feedService.postFeed(userId, request, images);
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
        feedService.deleteFeed(userId, feedId);
    }

    @PostMapping("/{feedId}/comment")
    public void postComment(@CurrentUser Long userId, @PathVariable("feedId") Long feedId,
        @RequestBody CommentRequest request) {
        commentService.comment(userId, feedId, request);
    }

    @PatchMapping("/{feedId}/comment/{commentId}")
    public void updateComment(@CurrentUser Long userId, @PathVariable("feedId") Long feedId,
        @PathVariable Long commentId,
        @RequestBody CommentRequest request) {
        commentService.updateComment(userId, feedId, commentId, request);
    }

    @DeleteMapping("/{feedId}/comment/{commentId}")
    public void deleteComment(@CurrentUser Long userId, @PathVariable("feedId") Long feedId,
        @PathVariable Long commentId) {
        commentService.deleteComment(userId, feedId, commentId);
    }
}
