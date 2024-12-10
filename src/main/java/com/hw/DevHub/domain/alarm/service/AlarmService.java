package com.hw.DevHub.domain.alarm.service;

import com.hw.DevHub.domain.alarm.mapper.AlarmMapper;
import com.hw.DevHub.domain.alarm.domain.Alarm;
import com.hw.DevHub.domain.alarm.dto.AlarmResponse;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmMapper alarmMapper;

    @Transactional
    public void sendAlarm(Long fromUserId, Long targetUserId) {
        alarmMapper.insertAlarm(
            Alarm.builder().fromUserId(fromUserId).toTargetId(targetUserId).build());
    }

    @Transactional(readOnly = true)
    public List<AlarmResponse> getAlarms(Long userId) {
        List<Alarm> alarms = alarmMapper.getAlarms(userId);
        List<AlarmResponse> res = new ArrayList<>();
        for (Alarm alarm : alarms) {
            res.add(AlarmResponse.builder().alarmId(alarm.getAlarmId()).fromUserId(
                    alarm.getFromUserId()).isRead(alarm.isRead()).createdAt(alarm.getCreatedAt())
                .build());
        }
        return res;
    }

    @Transactional
    public void readAlarm(Long userId, Long alarmId) {
        Alarm alarm = alarmMapper.getAlarm(alarmId, userId);
        if(alarm == null){
            throw new GlobalException(ErrorCode.ALARM_NOT_FOUND);
        }
        alarmMapper.readAlarm(alarmId, userId);
    }
}
