package com.hw.DevHub.global.annotation;

import com.hw.DevHub.domain.users.service.SessionLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionLoginService loginService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasAnnotation = parameter.hasParameterAnnotation(CurrentUser.class);
        boolean typeMatch = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && typeMatch;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return loginService.getCurrentUserId();
    }
}
