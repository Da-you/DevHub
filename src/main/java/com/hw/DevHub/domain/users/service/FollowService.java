package com.hw.DevHub.domain.users.service;

import com.hw.DevHub.domain.alarm.service.AlarmService;
import com.hw.DevHub.domain.users.dao.FollowRepository;
import com.hw.DevHub.domain.users.dao.UserRepository;
import com.hw.DevHub.domain.users.domain.Follow;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import com.hw.DevHub.infra.fcm.FCMPushService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final AlarmService alarmService;
    private final FCMPushService pushService;

    @Transactional
    public void followUser(Long userId, Long targetId) {
        User user = getUser(userId);
        User target = getUser(targetId);
        validatedSelfFollow(userId, targetId);

        if (isNotExistsTargetId(targetId)) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        } else if (checkExistsFollow(userId, targetId)) {
            throw new GlobalException(ErrorCode.ALREADY_RELATIONSHIP);
        }
        Follow relationship = Follow.builder()
            .fromUser(user)
            .toTarget(target)
            .build();
        followRepository.save(relationship);
        alarmService.sendAlarm(userId, targetId);

        pushService.sendFollowPushMessage(targetId);

    }


    @Transactional
    public void unfollowUser(Long userId, Long targetId) {
        validatedSelfFollow(userId, targetId);
        User from = getUser(userId);
        User target = getUser(targetId);
        if (isNotExistsTargetId(targetId)) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        } else if (checkExistsFollow(userId, targetId)) {
            throw new GlobalException(ErrorCode.ALREADY_RELATIONSHIP);
        }
        followRepository.deleteByFromUserAndToTarget(from, target);
    }

    private boolean isNotExistsTargetId(Long targetId) {
        return !userRepository.existsById(targetId);
    }


    private boolean checkExistsFollow(Long userId, Long targetId) {
        User from = getUser(userId);
        User target = getUser(targetId);
        return followRepository.existsByFromUserAndToTarget(from, target);
    }

    private User getUser(Long targetId) {
        return userRepository.findById(targetId)
            .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
    }

    private void validatedSelfFollow(Long userId, Long targetId) {
        if (userId.equals(targetId)) {
            throw new GlobalException(ErrorCode.UNABLE_TO_SELF_REQUEST);
        }
    }
}
