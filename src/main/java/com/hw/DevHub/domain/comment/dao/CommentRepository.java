package com.hw.DevHub.domain.comment.dao;

import com.hw.DevHub.domain.comment.domain.Comment;
import com.hw.DevHub.domain.feed.domain.Feed;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByFeedAndId(Feed feed, Long id);

    List<Comment> findAllByFeed(Feed feed);
}
