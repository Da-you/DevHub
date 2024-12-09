package com.hw.DevHub.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
public class FCMConfig {


    private final ClassPathResource pathResource;
    private final String projectId;

    public FCMConfig(@Value("${fcm.key.path}") String path,
        @Value("${fcm.key.project_id}") String projectId) {
        this.pathResource = new ClassPathResource(path);
        this.projectId = projectId;
    }

    // 빈의 초기화를 위해 모든 의존성이 주입이 된 후에 실행
    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials
                    .fromStream(pathResource.getInputStream()))
                .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

        } catch (IOException e) {
            log.error("Firebase 서버 연결에 실패 했습니다.");
        }
    }

    @Bean
    FirebaseMessaging firebaseMessaging() {
        log.info("Firebase 서버에 연결되었습니다.");
        return FirebaseMessaging.getInstance();
    }

    @Bean
    FirebaseApp firebaseApp() {
        return FirebaseApp.getInstance();
    }
}
