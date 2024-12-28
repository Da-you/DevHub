package com.hw.DevHub.domain.feed.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostFeedRequest {

        @NotNull(message = "피드의 내용을 입력해주세요.")
        private String content;
    }

}
