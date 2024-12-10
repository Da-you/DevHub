package com.hw.DevHub.domain.alarm.domain;

import com.hw.DevHub.domain.alarm.model.AlarmType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Alarm {

    private Long alarmId;
    private Long fromUserId;
    private Long targetUserId;
    private boolean isRead;
    private AlarmType alarmType;
    private LocalDateTime createdAt;

    @Builder
    public Alarm(Long fromUserId, Long toTargetId) {
        this.fromUserId = fromUserId;
        this.targetUserId = toTargetId;
        this.isRead = false;
        this.alarmType = AlarmType.FOLLOWING;
    }

}
