package com.hw.DevHub.domain.alarm.api;

import com.hw.DevHub.domain.alarm.dto.AlarmResponse;
import com.hw.DevHub.domain.alarm.service.AlarmService;
import com.hw.DevHub.global.annotation.CurrentUser;
import com.hw.DevHub.global.annotation.LoginRequired;
import com.hw.DevHub.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmApiController {

    private final AlarmService alarmService;


    @GetMapping
    @LoginRequired
    public ApiResponse<List<AlarmResponse>> getAlarms(@CurrentUser Long userId) {
        return ApiResponse.success(alarmService.getAlarms(userId));
    }

    @PatchMapping("/{alarmId}")
    @LoginRequired
    public void readAlarm(@CurrentUser Long userId, @PathVariable Long alarmId) {
        alarmService.readAlarm(userId, alarmId);
    }

}
