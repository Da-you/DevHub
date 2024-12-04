package com.hw.DevHub.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ImageRequest {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class ImageUploadDto {


        private Long feedId;
        private String imageName;
        private String imagePath;
        private int img_index;
    }

    @Getter
    @AllArgsConstructor
    public static class ImageInfo {

        public String fileName;
        public String filePath;
    }
}
