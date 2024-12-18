package com.hw.DevHub.domain.users.service;

import com.hw.DevHub.domain.feed.dto.FeedResponse.MypageFeeds;
import com.hw.DevHub.domain.feed.mapper.FeedMapper;
import com.hw.DevHub.domain.users.component.encryption.CustomEncryptionComponent;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.domain.users.dto.UserRequest.SignUpRequest;
import com.hw.DevHub.domain.users.dto.UserResponse.FollowCountResponse;
import com.hw.DevHub.domain.users.dto.UserResponse.MypageResponse;
import com.hw.DevHub.domain.users.mapper.FollowMapper;
import com.hw.DevHub.domain.users.mapper.UserMapper;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import com.hw.DevHub.infra.aws.storage.S3Component;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final FeedMapper feedMapper;
    private final FollowMapper followMapper;
    private final S3Component s3Component;
    private final CustomEncryptionComponent encryptionComponent;

    @Transactional
    public void addUser(SignUpRequest request) {

        String encodedPassword = encryptionComponent.encryptPassword(request.getEmail(),
            request.getPassword());

        SignUpRequest user = SignUpRequest.builder().email(request.getEmail())
            .password(encodedPassword).name(
                request.getName()).phoneNumber(request.getPhoneNumber())
            .nickname(request.getNickname()).build();
        userMapper.insertUser(user);
    }

    @Transactional(readOnly = true)
    public MypageResponse getMypage(Long userId, Long targetId) {
        User user = userMapper.findByUserId(targetId);
        FollowCountResponse follow = followMapper.followCount(targetId);
        List<MypageFeeds> feeds = feedMapper.getMypageFeeds(targetId);
        MypageResponse res = MypageResponse.builder()
            .nickname(user.getNickname())
            .profileImagePath(user.getProfileImagePath())
            .profileMessage(user.getProfileMessage())
            .countResponse(follow)
            .feedCount(feedMapper.getFeedCount(targetId))
            .feeds(feeds)
            .build();
        return res;
    }

    @Transactional
    public void updateProfileMessage(Long userId, String profileMessage) {
        User user = userMapper.findByUserId(userId);
        userMapper.updateProfileMessage(userId, profileMessage);
    }

    @Transactional
    public void updateProfileImagePath(Long userId, MultipartFile file) {
        User user = userMapper.findByUserId(userId);
        s3Component.uploadProfileImage(userId, file);
    }


    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        if (!userMapper.existsByEmail(email)) {
            return true;
        } else {
            throw new GlobalException(ErrorCode.DUPLICATED_EMAIL);
        }

    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        if (!userMapper.existsByNickname(nickname)) {
            return true;
        } else {
            throw new GlobalException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }

    @Transactional(readOnly = true)
    public boolean checkPhoneNumber(String phoneNumber) {
        if (!userMapper.existsByPhoneNumber(phoneNumber)) {
            return true;
        } else {
            throw new GlobalException(ErrorCode.DUPLICATED_PHONE_NUMBER);
        }
    }
}
