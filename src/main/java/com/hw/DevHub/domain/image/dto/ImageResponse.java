package com.hw.DevHub.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private String imageName;
    private String imagePath;
    private int imgIndex;
}
