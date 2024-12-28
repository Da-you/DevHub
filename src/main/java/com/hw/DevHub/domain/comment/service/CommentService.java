package com.hw.DevHub.domain.comment.service;

import com.hw.DevHub.domain.comment.dao.CommentRepository;
import com.hw.DevHub.domain.comment.domain.Comment;
import com.hw.DevHub.domain.comment.dto.CommentRequest;
import com.hw.DevHub.domain.feed.dao.FeedRepository;
import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.users.dao.UserRepository;
import com.hw.DevHub.domain.users.domain.User;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import java.security.PublicKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public void comment(Long userId, Long feedId, CommentRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));

        commentRepository.save(
            Comment.builder()
                .user(user)
                .feed(feed)
                .content(request.getComment())
                .build()
        );
    }

    @Transactional
    public void updateComment(Long userId, Long feedId, Long commentId, CommentRequest request) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));
        Comment comment = commentRepository.findByFeedAndId(feed, commentId)
            .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));
        if (comment.getUser().getUserId().equals(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }
        comment.updateContent(request.getComment());
    }

    @Transactional
    public void deleteComment(Long userId, Long feedId, Long commentId) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));
        Comment comment = commentRepository.findByFeedAndId(feed, commentId)
            .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }
        if (!feed.getUser().getUserId().equals(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }
        commentRepository.delete(comment);

    }

}
