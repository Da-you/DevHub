package com.hw.DevHub.infra.aws.storage;


import com.hw.DevHub.domain.image.dto.ImageRequest.ImageInfo;
import com.hw.DevHub.domain.image.dto.ImageRequest.ImageUploadDto;
import com.hw.DevHub.domain.image.dto.ImageResponse;
import com.hw.DevHub.domain.image.exception.ImageUploadException;
import com.hw.DevHub.domain.image.mapper.ImageMapper;
import com.hw.DevHub.domain.users.mapper.UserMapper;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Component {

    private final S3Client s3Client;
    private final ImageMapper imageMapper;
    private final UserMapper userMapper;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void uploadImages(Long feedId, List<MultipartFile> images) {
        List<ImageInfo> infos = uploadImagesToS3(images, feedId);
        List<ImageUploadDto> uploadDtos = new ArrayList<>();
        for (int i = 0; i < infos.size(); i++) {
            uploadDtos.add(ImageUploadDto.builder()
                .feedId(feedId)
                .imageName(infos.get(i).getFileName())
                .imagePath(infos.get(i).getFilePath())
                .img_index(i + 1)
                .build());
        }
        imageMapper.insertImages(uploadDtos);
    }

    @Transactional
    public void uploadProfileImage(Long userId, MultipartFile file) {
        ImageInfo imageInfo = putS3Profile(file, userId, file.getOriginalFilename());
        userMapper.updateProfileImage(userId, imageInfo.getFilePath());
    }

    // s3에 업로드
    public List<ImageInfo> uploadImagesToS3(List<MultipartFile> files, Long feedId) {
        HashMap<String, String> newFileName = FileNameUtil.changeFileNames(files);
        List<ImageInfo> imageInfos = new ArrayList<>();
        for (MultipartFile file : files) {
            ImageInfo info = putS3(file, feedId,
                newFileName.get(file.getOriginalFilename()));
            imageInfos.add(info);
        }
        return imageInfos;
    }

    public ImageInfo uploadImageToS3(MultipartFile file, Long userId) {

        String newFileName = String.valueOf(FileNameUtil.createNewFileName(file));
        ImageInfo image = putS3Profile(file, userId, newFileName);
        return image;
    }


    private ImageInfo putS3(MultipartFile file, Long feedId, String newFileName) {
        StringBuilder key = new StringBuilder();
        UUID uuid = UUID.randomUUID();
        key.append("feed").append("/")
            .append(feedId).append("/")
            .append(uuid).append("_")
            .append(LocalDateTime.now()).append("_")
            .append(newFileName);
        // feed/1/uuid_20241204:hh:mm:ss_newFileName.jpg
        log.info("key : {}", key.toString());

        try {
            byte[] attachemnt = file.getBytes(); // MultipartFile을 바이트 배열로 변환
            s3Client.putObject( // s3 버킷에 업로드
                PutObjectRequest.builder().bucket(bucket).key(String.valueOf(key)).build(),
                RequestBody.fromByteBuffer(ByteBuffer.wrap(attachemnt)) // s3에 적합현 형식으로 변환
            );

            URL reportUrl = s3Client.utilities() // s3에 업로드된 이미지의 url을 생성
                .getUrl(GetUrlRequest.builder().bucket(bucket).key(key.toString()).build());
            log.info("s3에 업로드된 이미지의 url : {}", reportUrl);
            ImageInfo imageInfo = new ImageInfo(newFileName, String.valueOf(reportUrl));
            return imageInfo;
        } catch (IOException e) {
            throw new ImageUploadException("이미지 업로드에 실패했습니다.", e);
        }


    }

    private ImageInfo putS3Profile(MultipartFile file, Long userId, String newFileName) {
        StringBuilder key = new StringBuilder();
        UUID uuid = UUID.randomUUID();
        key.append("profile").append("/")
            .append(userId).append("/")
            .append(uuid).append("_")
            .append(LocalDateTime.now()).append("_")
            .append(newFileName);
        // feed/1/uuid_20241204:hh:mm:ss_newFileName.jpg
        log.info("key : {}", key.toString());

        try {
            byte[] attachemnt = file.getBytes(); // MultipartFile을 바이트 배열로 변환
            s3Client.putObject( // s3 버킷에 업로드
                PutObjectRequest.builder().bucket(bucket).key(String.valueOf(key)).build(),
                RequestBody.fromByteBuffer(ByteBuffer.wrap(attachemnt)) // s3에 적합현 형식으로 변환
            );

            URL reportUrl = s3Client.utilities() // s3에 업로드된 이미지의 url을 생성
                .getUrl(GetUrlRequest.builder().bucket(bucket).key(key.toString()).build());
            log.info("s3에 업로드된 이미지의 url : {}", reportUrl);
            ImageInfo imageInfo = new ImageInfo(newFileName, String.valueOf(reportUrl));
            return imageInfo;
        } catch (IOException e) {
            throw new ImageUploadException("이미지 업로드에 실패했습니다.", e);
        }


    }

    public void delete(String s3Path) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket)
            .key(s3Path).build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    public void deleteFeedImage(Long feedId) {
        List<String> imagePaths = imageMapper.getImagePathByFeedId(feedId);
        for (String imagePath : imagePaths) {
            delete(imagePath);
        }
        imageMapper.deleteImages(feedId);

    }

    public List<ImageResponse> getImages(Long feedId) {
        return imageMapper.getImages(feedId);
    }

}
