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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
        Feed feed = feedRepository.findByFeedId(feedId)
            .orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));
        FeedLike like = feedLikeRepository.findFeedLikeByUserAndFeed(user, feed);
        if (like == null) {
            log.info("null 체킹 호출 user {}, feed {}", userId, feedId);
            feedLikeRepository.save(
                FeedLike.builder()
                    .user(user)
                    .feed(feed)
                    .build()
            );
            feed.addLike();
        } else {
            log.info("상태 변화");
            like.updateStatus();
        }
    }

    @Transactional
    public boolean isLike(User user, Feed feed) {
        boolean exist = feedLikeRepository.existsFeedLikeByUserAndFeed(user, feed);
        log.info("Check if user {} likes feed {}: {}", user.getUserId(), feed.getFeedId(), exist);
        return exist;
    }

    @Transactional
    public void feedLikeCancel(User user, Feed feed) {
        FeedLike like = feedLikeRepository.findFeedLikeByUserAndFeed(user, feed);
        log.info("like canceled 호출: {}", like);
        feed.removeLike();
    }

    @Transactional
    public void likeStatus(FeedLike like) {
        like.updateStatus();
    }

}
