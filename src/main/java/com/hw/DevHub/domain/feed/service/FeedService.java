package com.hw.DevHub.domain.feed.service;

import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.feed.dto.FeedRequest;
import com.hw.DevHub.domain.feed.dto.FeedRequest.PostFeedRequest;
import com.hw.DevHub.domain.feed.dto.FeedResponse;
import com.hw.DevHub.domain.feed.dto.FeedResponse.ViewFeed;
import com.hw.DevHub.domain.feed.mapper.FeedMapper;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.domain.users.mapper.UserMapper;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedMapper feedMapper;
    private final UserMapper userMapper;

    @Transactional
    public void postFeed(Long userId, PostFeedRequest request) {
        User user = userMapper.findByUserId(userId);
        Feed feed = Feed.builder()
            .userId(userId)
            .content(request.getContent())
            .build();
        feedMapper.insertFeed(feed);
    }

    @Transactional(readOnly = true)
    public List<ViewFeed> getFeeds(Long userId) {
        List<ViewFeed> res = new ArrayList<>();
        List<Feed> feeds = feedMapper.getFeeds();
        for (Feed feed : feeds) {
            User author = userMapper.findByUserId(feed.getUserId());
            res.add(ViewFeed.builder().feedId(feed.getFeedId())
                .profileImagePath(author.getProfileImagePath())
                .nickname(author.getNickname()).content(
                    feed.getContent()).createTime(feed.getCreatedAt()).build());
        }
        return res;
    }

    @Transactional(readOnly = true)
    public ViewFeed getFeedById(Long userId, Long feedId) {
        Feed feed = feedMapper.getFeedById(feedId);
        User author = userMapper.findByUserId(userId);
        return ViewFeed.builder()
            .feedId(feedId)
            .profileImagePath(author.getProfileImagePath())
            .nickname(author.getNickname())
            .content(feed.getContent())
            .createTime(feed.getCreatedAt())
            .build();
    }

    @Transactional
    public void updateFeed(Long userId, Long feedId, PostFeedRequest request) {
        Feed feed = feedMapper.getFeedById(feedId);
        if (!feed.getUserId().equals(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }
        feedMapper.updateFeed(feedId, request.getContent());
    }

    @Transactional
    public void deleteFeed(Long userId, Long feedId) {
        Feed feed = feedMapper.getFeedById(feedId);
        if (!feed.getUserId().equals(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }
        feedMapper.deleteFeedById(feedId);
    }
}
