package com.hw.DevHub.domain.like.dao;

import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.like.domain.FeedLike;
import com.hw.DevHub.domain.users.domain.User;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

    boolean existsFeedLikeByUserAndFeed(User user, Feed feed);

    @Lock(LockModeType.PESSIMISTIC_READ)
    FeedLike findFeedLikeByUserAndFeed(User user, Feed feed);

    List<FeedLike> findAllByFeed(Feed feed);
}
