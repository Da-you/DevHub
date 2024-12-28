package com.hw.DevHub.domain.like.service;

import com.hw.DevHub.domain.feed.dao.FeedRepository;
import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.like.dao.FeedLikeRepository;
import com.hw.DevHub.domain.like.domain.FeedLike;
import com.hw.DevHub.domain.users.dao.UserRepository;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {


    private final FeedLikeRepository feedLikeRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Transactional
    public void feedLike(Long userId, Long feedId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));
        if (isLike(user, feed)) {
            feedLikeCancel(user, feed);
        } else {
            feedLikeRepository.save(
                FeedLike.builder()
                    .user(user)
                    .feed(feed)
                    .build()
            );
        }
    }

    private boolean isLike(User user, Feed feed) {
        return feedLikeRepository.existsFeedLikeByUserAndFeed(user, feed);
    }

    @Transactional
    public void feedLikeCancel(User user, Feed feed) {
        FeedLike like = feedLikeRepository.findFeedLikeByUserAndFeed(user, feed);
        feedLikeRepository.delete(like);
    }

}
