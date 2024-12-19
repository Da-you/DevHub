package com.hw.DevHub.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass // 해당 츄상클래스를 상속안 엔티티는 해당 추상클래스에 정의된 필드를 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing 자동 추가
public abstract class BaseTimeEntity {

    @Column(updatable = false,name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;


    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

}
