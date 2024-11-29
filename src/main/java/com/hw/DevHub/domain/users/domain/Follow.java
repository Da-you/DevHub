package com.hw.DevHub.domain.users.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Follow {

    private Long fromUserId; // 팔로우 요청을 보내는 사용자 Id
    private Long toTargetId; // 팔로우 당하는 사용자 Id
    private LocalDateTime createdAt;

    @Builder
    public Follow(Long fromUserId, Long toTargetId) {
        this.fromUserId = fromUserId;
        this.toTargetId = toTargetId;
    }
}
