package com.hw.devhub.domain.alarm.service;

import com.hw.devhub.domain.alarm.dao.AlarmRepository;
import com.hw.devhub.domain.alarm.domain.Alarm;
import com.hw.devhub.domain.alarm.dto.AlarmResponse;
import com.hw.devhub.domain.users.dao.UserRepository;
import com.hw.devhub.domain.users.domain.User;
import com.hw.devhub.global.exception.ErrorCode;
import com.hw.devhub.global.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendAlarm(Long fromUserId, Long targetUserId) {
        User from = getUser(fromUserId);
        User target = getUser(targetUserId);
        alarmRepository.save(
            Alarm.builder().fromUser(from).toTarget(target).build());
    }

    @Transactional(readOnly = true)
    public List<AlarmResponse> getAlarms(Long userId) {
        User user = getUser(userId);
        List<Alarm> alarms = alarmRepository.findAllByToTarget(user);
        List<AlarmResponse> res = new ArrayList<>();
        for (Alarm alarm : alarms) {
            res.add(AlarmResponse.builder().alarmId(alarm.getAlarmId()).fromUserId(
                    alarm.getFromUser().getUserId()).isRead(alarm.isRead())
                .createdAt(alarm.getCreatedAt())
                .build());
        }
        return res;
    }

    @Transactional
    public void readAlarm(Long userId, Long alarmId) {
        User user = getUser(userId);
        Alarm alarm = alarmRepository.findByAlarmIdAndToTarget(alarmId, user);
        if (alarm == null) {
            throw new GlobalException(ErrorCode.ALARM_NOT_FOUND);
        }
        alarm.readAlarm();
    }

    private User getUser(Long targetId) {
        return userRepository.findById(targetId)
            .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
    }
}
