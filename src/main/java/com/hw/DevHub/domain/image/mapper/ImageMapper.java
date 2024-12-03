package com.hw.DevHub.domain.image.mapper;

import com.hw.DevHub.domain.image.dto.ImageRequest.ImageUploadDto;
import com.hw.DevHub.domain.image.dto.ImageResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

    void insertImages(List<ImageUploadDto> images);

    List<ImageResponse> getImages(Long feedId);

    List<String> getImagePathByFeedId(Long feedId);

    void deleteImages(Long feedId);

    void deleteImageByFeedIdAndIndex(Long feedId, Long img_index);

}
