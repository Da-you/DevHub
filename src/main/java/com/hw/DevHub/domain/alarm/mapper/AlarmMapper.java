package com.hw.DevHub.domain.alarm.mapper;

import com.hw.DevHub.domain.alarm.domain.Alarm;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AlarmMapper {

    void insertAlarm(Alarm alarm);

    void readAlarm(@Param("alarmId") Long alarmId, @Param("targetUserId") Long targetUserId);

    List<Alarm> getAlarms(Long targetUserId);

    Alarm getAlarm(@Param("alarmId") Long alarmId, @Param("targetUserId") Long targetUserId);
}
