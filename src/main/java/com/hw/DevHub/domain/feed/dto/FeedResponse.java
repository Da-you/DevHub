package com.hw.DevHub.domain.feed.dto;

import com.hw.DevHub.domain.comment.dto.CommentResponse;
import com.hw.DevHub.domain.image.dto.ImageResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedResponse {

    @Getter
    @NoArgsConstructor
    public static class ViewFeed {

        private Long feedId;
        private String profileImagePath;
        private String nickname;
        private String content;
        private List<ImageResponse> images;
        private int likeCount;
        private int commentCount;
        private List<CommentResponse> comments;
        private LocalDateTime createdAt;

        @Builder
        public ViewFeed(Long feedId, String profileImagePath, String nickname, String content,
            List<ImageResponse> images, int likeCount, int commentCount,
            List<CommentResponse> comments,
            LocalDateTime createdAt) {
            this.feedId = feedId;
            this.profileImagePath = profileImagePath;
            this.nickname = nickname;
            this.content = content;
            this.images = images;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
            this.comments = comments;
            this.createdAt = createdAt;
        }

        @Builder
        public ViewFeed(Long feedId, String profileImagePath, String nickname, String content,
            List<ImageResponse> images, int likeCount, int commentCount, LocalDateTime createdAt) {
            this.feedId = feedId;
            this.profileImagePath = profileImagePath;
            this.nickname = nickname;
            this.content = content;
            this.images = images;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
            this.createdAt = createdAt;
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MypageFeeds {

        private Long feedId;
        private String thumbnailPath;
        private int likeCount;
        private int commentCount;
        private LocalDateTime createdAt;
    }

}
