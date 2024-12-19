package com.hw.DevHub.domain.users.domain;

import com.hw.DevHub.domain.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;
    @JoinColumn(name = "from_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser; // 팔로우 요청을 보내는 사용자 Id
    @JoinColumn(name = "to_target_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User toTarget; // 팔로우 당하는 사용자 Id

    @Builder
    public Follow(User fromUser, User toTarget) {
        this.fromUser = fromUser;
        this.toTarget = toTarget;
    }
}
