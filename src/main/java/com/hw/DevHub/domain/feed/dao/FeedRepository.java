package com.hw.DevHub.domain.feed.dao;

import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.users.domain.User;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select f from Feed f where f.feedId = :feedId")
    Optional<Feed> findByFeedId(Long feedId);

    List<Feed> findAllByUser(User user);
}
