package com.hw.DevHub.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private String code; // 상태 코드
    private T data; // 출력 데이터
    private String message;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("200",data,"api success");
    }
    public static ApiResponse<Void> fail(String errorCode,String message){
        return new ApiResponse<>(errorCode,null,message);
    }
}
