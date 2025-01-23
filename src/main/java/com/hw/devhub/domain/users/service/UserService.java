package com.hw.devhub.domain.users.service;

import com.hw.devhub.domain.feed.dao.FeedRepository;
import com.hw.devhub.domain.feed.domain.Feed;
import com.hw.devhub.domain.feed.dto.FeedResponse.MypageFeeds;
import com.hw.devhub.domain.model.Position;
import com.hw.devhub.domain.users.component.encryption.CustomEncryptionComponent;
import com.hw.devhub.domain.users.dao.FollowRepository;
import com.hw.devhub.domain.users.dao.UserRepository;
import com.hw.devhub.domain.users.domain.User;
import com.hw.devhub.domain.users.dto.UserRequest.SignUpRequest;
import com.hw.devhub.domain.users.dto.UserResponse.FollowCountResponse;
import com.hw.devhub.domain.users.dto.UserResponse.MypageResponse;
import com.hw.devhub.global.exception.ErrorCode;
import com.hw.devhub.global.exception.GlobalException;
import com.hw.devhub.infra.aws.storage.S3Component;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FeedRepository feedRepository;
    private final S3Component s3Component;
    private final CustomEncryptionComponent encryptionComponent;

    @Transactional
    public void addUser(SignUpRequest request) {
        checkEmail(request.getEmail());
        checkNickname(request.getNickname());
        String encodedPassword = encryptionComponent.encryptPassword(request.getEmail(),
            request.getPassword());

        User insertUser = User.builder().email(request.getEmail()).password(encodedPassword).name(
                request.getName()).phoneNumber(request.getPhoneNumber()).nickname(request.getNickname())
            .build();
        userRepository.save(insertUser);
    }

    @Transactional(readOnly = true)
    public MypageResponse getMypage(Long targetId) {
        User user = getUser(targetId);
        int follower = followRepository.countByToTarget(user);
        int following = followRepository.countByFromUser(user);

        List<MypageFeeds> feeds = new ArrayList<>();
        List<Feed> feedList = feedRepository.findAllByUser(user);
        for (Feed feed : feedList) {
            feeds.add(MypageFeeds.builder()
                .feedId(feed.getFeedId())
                .thumbnailPath(feed.getImages().getFirst().getImagePath())
                .likeCount(feed.getLikeCount())
                .commentCount(feed.getCommentCount())
                .createdAt(feed.getCreatedAt())
                .build()
            );
        }
        return MypageResponse.builder()
            .nickname(user.getNickname())
            .profileImagePath(user.getProfileImagePath())
            .profileMessage(user.getProfileMessage())
            .countResponse(FollowCountResponse.builder().followerCount(follower)
                .followingCount(following).build())
            .feedCount(feedList.size())
            .feeds(feeds)
            .build();
    }

    @Transactional
    public void updateProfileMessage(Long userId, String profileMessage) {
        User user = getUser(userId);
        user.updateProfileMessage(profileMessage);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void updateProfileImagePath(Long userId, MultipartFile file) {
        User user = getUser(userId);
        String path = s3Component.uploadProfileImage(userId, file);
        user.updateProfileImagePath(path);
    }
    @Transactional
    public void updatePosition(Long userId, Position position) {
        User user = getUser(userId);
        user.updatePosition(position);
    }


    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            return true;
        } else {
            throw new GlobalException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        if (!userRepository.existsByNickname(nickname)) {
            return true;
        } else {
            throw new GlobalException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }

    @Transactional(readOnly = true)
    public boolean checkPhoneNumber(String phoneNumber) {
        if (!userRepository.existsByPhoneNumber(phoneNumber)) {
            return true;
        } else {
            throw new GlobalException(ErrorCode.DUPLICATED_PHONE_NUMBER);
        }
    }
}
