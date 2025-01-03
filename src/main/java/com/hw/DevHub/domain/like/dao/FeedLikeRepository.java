package com.hw.DevHub.domain.like.dao;

import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.like.domain.FeedLike;
import com.hw.DevHub.domain.users.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

    boolean existsFeedLikeByUserAndFeed(User user, Feed feed);

    FeedLike findFeedLikeByUserAndFeed(User user, Feed feed);

    List<FeedLike> findAllByFeed(Feed feed);
}
