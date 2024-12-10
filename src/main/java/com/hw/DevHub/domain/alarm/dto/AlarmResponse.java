package com.hw.DevHub.domain.alarm.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AlarmResponse {

    private Long alarmId;
    private Long fromUserId;
    private boolean isRead;
    private LocalDateTime createdAt;
}
