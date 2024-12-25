package com.hw.DevHub.domain.feed.dao;

import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.users.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Optional<Feed> findByFeedId(Long feedId);

    List<Feed> findAllByUser(User user);
}
