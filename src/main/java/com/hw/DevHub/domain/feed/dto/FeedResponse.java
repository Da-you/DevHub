package com.hw.DevHub.domain.feed.dto;

import java.time.LocalDateTime;
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
        private LocalDateTime createTime;
    }

}
