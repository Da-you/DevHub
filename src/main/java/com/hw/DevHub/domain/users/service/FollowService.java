package com.hw.DevHub.domain.users.service;

import com.hw.DevHub.domain.alarm.service.AlarmService;
import com.hw.DevHub.domain.users.domain.Follow;
import com.hw.DevHub.domain.users.mapper.FollowMapper;
import com.hw.DevHub.domain.users.mapper.UserMapper;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import com.hw.DevHub.infra.fcm.FCMPushService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowMapper followMapper;
    private final UserMapper userMapper;
    private final AlarmService alarmService;
    private final FCMPushService pushService;

    @Transactional
    public void followUser(Long userId, Long targetId) {
        validatedSelfFollow(userId, targetId);

        if (isNotExistsTargetId(targetId)) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        } else if (checkExistsFollow(userId, targetId)) {
            throw new GlobalException(ErrorCode.ALREADY_RELATIONSHIP);
        }
        Follow relationship = Follow.builder()
            .fromUserId(userId)
            .toTargetId(targetId)
            .build();
        followMapper.insertFollower(relationship);
        alarmService.sendAlarm(userId, targetId);

        pushService.sendFollowPushMessage(targetId);

    }


    @Transactional
    public void unfollowUser(Long userId, Long targetId) {
        validatedSelfFollow(userId, targetId);

        if (isNotExistsTargetId(targetId)) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        } else if (!checkExistsFollow(userId, targetId)) {
            throw new GlobalException(ErrorCode.ALREADY_RELATIONSHIP);
        }
        followMapper.deleteFollower(userId, targetId);
    }

    private boolean isNotExistsTargetId(Long targetId) {
        return !userMapper.existsById(targetId);
    }


    private boolean checkExistsFollow(Long userId, Long targetId) {
        return followMapper.existsFollower(userId, targetId);
    }

    private void validatedSelfFollow(Long userId, Long targetId) {
        if (userId.equals(targetId)) {
            throw new GlobalException(ErrorCode.UNABLE_TO_SELF_REQUEST);
        }
    }
}
