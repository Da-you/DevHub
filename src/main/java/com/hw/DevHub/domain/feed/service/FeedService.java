package com.hw.DevHub.domain.feed.service;

import com.hw.DevHub.domain.comment.dao.CommentRepository;
import com.hw.DevHub.domain.comment.domain.Comment;
import com.hw.DevHub.domain.comment.dto.CommentResponse;
import com.hw.DevHub.domain.feed.dao.FeedRepository;
import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.feed.dto.FeedRequest.PostFeedRequest;
import com.hw.DevHub.domain.feed.dto.FeedResponse.ViewFeed;
import com.hw.DevHub.domain.image.dto.ImageResponse;
import com.hw.DevHub.domain.like.dao.FeedLikeRepository;
import com.hw.DevHub.domain.like.domain.FeedLike;
import com.hw.DevHub.domain.users.dao.UserRepository;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.domain.users.dto.UserResponse.UserInfo;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import com.hw.DevHub.infra.aws.storage.S3Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FeedLikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final S3Component s3Component;

    @Transactional
    public void postFeed(Long userId, PostFeedRequest request, List<MultipartFile> images) {
        User user = getUser(userId);
        Feed feed = Feed.builder()
            .user(user)
            .content(request.getContent())
            .build();
        feedRepository.save(feed);
        s3Component.uploadImages(feed, images);

    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<ViewFeed> getFeeds(Long userId) {
        User user = getUser(userId);
        List<ViewFeed> res = new ArrayList<>();
        List<Feed> feeds = feedRepository.findAllByUser(user);
        for (Feed feed : feeds) {
            User author = getUser(userId);
            res.add(ViewFeed.builder()
                .feedId(feed.getFeedId())
                .profileImagePath(author.getProfileImagePath())
                .nickname(author.getNickname())
                .content(feed.getContent())
                .images(s3Component.getImages(feed))
                .likeCount(feed.getLikeCount())
                .commentCount(feed.getCommentCount())
                .createdAt(feed.getCreatedAt())
                .build());
        }
        return res;
    }

    @Transactional(readOnly = true)
    public ViewFeed getFeedById(Long userId, Long feedId) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));
        User author = getUser(userId);
        List<ImageResponse> images = s3Component.getImages(feed);
        List<Comment> commentList = commentRepository.findAllByFeed(feed);
        List<CommentResponse> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            comments.add(
                CommentResponse.builder()
                    .commentId(comment.getId())
                    .profileImagePath(comment.getUser().getProfileImagePath())
                    .nickname(comment.getUser().getNickname())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .build()
            );
        }
        return ViewFeed.builder()
            .feedId(feedId)
            .profileImagePath(author.getProfileImagePath())
            .nickname(author.getNickname())
            .content(feed.getContent())
            .likeCount(feed.getLikeCount())
            .commentCount(feed.getCommentCount())
            .comments(comments)
            .createdAt(feed.getCreatedAt())
            .images(images)
            .build();
    }

    @Transactional
    public void updateFeed(Long userId, Long feedId, PostFeedRequest request) {
        Feed feed = feedRepository.findByFeedId(feedId)
            .orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));
        if (!feed.getUser().getUserId().equals(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }
        feed.updateFeed(request.getContent());
    }

    @Transactional
    public void deleteFeed(Long userId, Long feedId) {
        Feed feed = feedRepository.findByFeedId(feedId)
            .orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));
        if (!feed.getUser().getUserId().equals(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }
        s3Component.deleteFeedImage(feed);
    }

    @Transactional(readOnly = true)
    public Set<UserInfo> getFeedLikeUsers(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));
        Set<UserInfo> res = new HashSet<>();
        List<FeedLike> likes = likeRepository.findAllByFeed(feed);
        likes.stream().map(like -> UserInfo.builder()
                .nickname(like.getUser().getNickname())
                .profileImagePath(like.getUser().getProfileImagePath()).build())
            .forEach(res::add);
        return res;
    }
}
