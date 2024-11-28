package com.hw.DevHub.domain.feed.domain;

import com.hw.DevHub.domain.users.domain.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Feed {

    private Long feedId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public Feed(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
