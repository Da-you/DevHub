package com.hw.DevHub.global.exception;

import com.hw.DevHub.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> applicationHandler(GlobalException e) {
        log.error("error occurs --> {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(ApiResponse.fail(e.getErrorCode().toString()));
    }
}
