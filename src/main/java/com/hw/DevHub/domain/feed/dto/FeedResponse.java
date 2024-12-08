package com.hw.DevHub.domain.feed.dto;

import com.hw.DevHub.domain.image.dto.ImageResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ViewFeed {

        private Long feedId;
        private String profileImagePath;
        private String nickname;
        private String content;
        private List<ImageResponse> images;
        private LocalDateTime createTime;
    }
    @Getter
    @AllArgsConstructor
    public static class MypageFeeds {

        private Long feedId;
        private String thumbnailPath;
        private LocalDateTime createdAt;
    }

}
