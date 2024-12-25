package com.hw.DevHub.domain.alarm.domain;

import com.hw.DevHub.domain.alarm.model.AlarmType;
import com.hw.DevHub.domain.model.BaseTimeEntity;
import com.hw.DevHub.domain.users.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;
    @JoinColumn(name = "from_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser; // 팔로우 요청을 보내는 사용자 Id
    @JoinColumn(name = "to_target_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User toTarget; // 팔로우 당하는 사용자 Id
    private boolean isRead;
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Builder
    public Alarm(User fromUser, User toTarget) {
        this.fromUser = fromUser;
        this.toTarget = toTarget;
        this.isRead = false;
        this.alarmType = AlarmType.FOLLOWING;
    }

    public void readAlarm() {
        this.isRead = true;
    }

}
