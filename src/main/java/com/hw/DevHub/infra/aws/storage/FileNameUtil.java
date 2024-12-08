package com.hw.DevHub.infra.aws.storage;


import com.hw.DevHub.domain.image.exception.ImageExtensionException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileNameUtil {

    public static boolean validImageFileExtension(String fileName) {
        int lastOfIndex = fileName.lastIndexOf("."); // 문자열에서 마지막에서 '.' 을 찾는다.
        if (lastOfIndex == -1) { // image.jpg 라면 lastOfIndex == 5 이며 만약에 '.' 을 찾지 못한다면 -1을 반환한다.
            throw new ImageExtensionException("이미지 확장자는 jpg, jpeg, png, gif 만 사용 가능합니다.");
        }

        String extension = fileName.substring(lastOfIndex + 1).toLowerCase();
        List<String> allowedExtension = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtension.contains(extension)) {
            throw new ImageExtensionException("이미지 확장자는 jpg, jpeg, png, gif 만 사용 가능합니다.");
        }
        return true;
    }

    public static HashMap<String, String> changeFileNames(List<MultipartFile> files) {

        HashMap<String, String> newFileNames = new HashMap<>();

        for (MultipartFile file : files) {
            newFileNames.put(file.getOriginalFilename(), String.valueOf(createNewFileName(file)));
        }
        return newFileNames;
    }


    public static HashMap<String, String> changeFileName(MultipartFile file) {
        HashMap<String, String> newFileName = new HashMap<>();
        newFileName.put(file.getOriginalFilename(), String.valueOf(createNewFileName(file)));
        return newFileName;
    }

    public static StringBuilder createNewFileName(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        StringBuilder newFileName = new StringBuilder().append(uuid).append(".").append(extension);
        validImageFileExtension(newFileName.toString());
        return newFileName;
    }
}
