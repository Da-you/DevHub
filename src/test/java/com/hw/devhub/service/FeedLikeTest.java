package com.hw.devhub.service;


import com.hw.devhub.domain.comment.dto.CommentRequest;
import com.hw.devhub.domain.comment.service.CommentService;
import com.hw.devhub.domain.feed.dao.FeedRepository;
import com.hw.devhub.domain.feed.domain.Feed;
import com.hw.devhub.domain.like.dao.FeedLikeRepository;
import com.hw.devhub.domain.like.service.LikeService;
import com.hw.devhub.domain.users.dao.UserRepository;

import jakarta.transaction.Transactional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("jpa")
@SpringBootTest
public class FeedLikeTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedLikeRepository feedLikeRepository;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentService commentService;



    @Test
    @Transactional
    void currencyTest() throws InterruptedException {
        Long feedId = 6L;
        int count = 24;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++) {
            Long userId = (i % 2 == 0) ? 2L : 3L;
            executorService.execute(() -> {
                try {
                    likeService.feedLike(userId, feedId);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        Feed feed = feedRepository.findByFeedId(feedId).orElseThrow(RuntimeException::new);
        Assertions.assertThat(feed.getLikeCount()).isEqualTo(2);
    }

    @Test
    void commentCurrencyTest() throws InterruptedException {
        CommentRequest commentRequest = new CommentRequest("하이");
        Long feedId = 10L;
        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++) {
            Long userId = (i % 2 == 0) ? 2L : 3L;
            executorService.execute(() -> {
                try {
                    commentService.comment(userId, feedId,commentRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        Feed feed = feedRepository.findById(feedId).orElseThrow(RuntimeException::new);
        System.out.println(feed.getCommentCount());
        Assertions.assertThat(feed.getCommentCount()).isEqualTo(100);
    }
}
