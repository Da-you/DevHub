package com.hw.DevHub.global.interceptor;

import com.hw.DevHub.domain.users.service.SessionLoginService;
import com.hw.DevHub.global.annotation.LoginRequired;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginRequiredInterceptor implements HandlerInterceptor {

    private final SessionLoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);
            if (loginRequired == null) {
                return true;
            }
            if (loginService.getCurrentUserId() == null) {
                throw new GlobalException(ErrorCode.UNAUTHENTICATED_USER);
            }
        }
        return true;
    }
}
