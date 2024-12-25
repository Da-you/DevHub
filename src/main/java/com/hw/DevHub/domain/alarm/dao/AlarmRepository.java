package com.hw.DevHub.domain.alarm.dao;

import com.hw.DevHub.domain.alarm.domain.Alarm;
import com.hw.DevHub.domain.users.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByToTarget(User user);

    Alarm findByAlarmIdAndToTarget(Long alarmId, User toTarget);
}
