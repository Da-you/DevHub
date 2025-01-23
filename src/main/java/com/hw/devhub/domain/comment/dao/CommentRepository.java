package com.hw.devhub.domain.comment.dao;

import com.hw.devhub.domain.comment.domain.Comment;
import com.hw.devhub.domain.feed.domain.Feed;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByFeedAndId(Feed feed, Long id);

    List<Comment> findAllByFeed(Feed feed);
}
