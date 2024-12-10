package com.hw.DevHub.infra.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.hw.DevHub.domain.alarm.model.AlarmType;
import com.hw.DevHub.infra.fcm.dto.FCMMessageRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMPushService {

    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, String> redisTemplate;

    private final MessageSource messageSource;
    @Value("${fcm.key.path}")
    private String SERVICE_ACCOUNT;
    @Value("${fcm.key.url}")
    private String API_URL;


    public void setToken(Long userId) {
        String key = String.valueOf(userId);
        redisTemplate.opsForValue().set(key, getAccessToken());
        redisTemplate.expire(key, Duration.ofHours(1));
    }

    public String getToken(Long userId) {
        String key = String.valueOf(userId);
        if (!redisTemplate.hasKey(key)) {
            setToken(userId);
        }

        return redisTemplate.opsForValue().get(key);
    }

    public void deleteToken(Long userId) {
        String key = String.valueOf(userId);
        redisTemplate.delete(key);
    }


    private String makeMessage(String token) throws JsonProcessingException {
        FCMMessageRequest fcmMessage = FCMMessageRequest.builder()
            .message(FCMMessageRequest.Message.builder().token(token).notification(
                    FCMMessageRequest.Notification.builder().title(AlarmType.FOLLOWING.name())
                        .body("push.alarm.following")
                        .build())
                .build()).validateOnly(false).build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    public void sendFollowPushMessage(Long targetUserId) {
        try {
            String message = makeMessage(getToken(targetUserId));

            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));

            Request request = new Request.Builder().url(API_URL).post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

            Response response = client.newCall(request).execute();
            log.info(response.body().string());
        } catch (IOException e) {
            log.error(e.getMessage(), targetUserId);
        }
    }

    private String getAccessToken() {
        try {
            GoogleCredentials credential = GoogleCredentials.fromStream(
                    new ClassPathResource(SERVICE_ACCOUNT).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
            credential.refreshIfExpired();
            log.info("getAccessToken() - googleCredentials: {} ",
                credential.getAccessToken().getTokenValue());
            return credential.getAccessToken().getTokenValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
