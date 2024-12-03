package com.hw.DevHub.domain.image.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image {

    private Long feedId;
    private String imageName;
    private String imagePath;
    private int img_index;
    private LocalDateTime createdAt;

    @Builder
    public Image(int img_index, Long feedId, String imageName, String imagePath) {
        this.img_index = img_index;
        this.feedId = feedId;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
