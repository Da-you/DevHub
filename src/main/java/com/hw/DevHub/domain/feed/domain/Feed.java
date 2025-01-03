package com.hw.DevHub.domain.feed.domain;

import com.hw.DevHub.domain.comment.domain.Comment;
import com.hw.DevHub.domain.image.domain.Image;
import com.hw.DevHub.domain.like.domain.FeedLike;
import com.hw.DevHub.domain.model.BaseTimeEntity;
import com.hw.DevHub.domain.users.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private int likeCount;
    @Column(nullable = false)
    private int commentCount;

    @OneToMany(mappedBy = "feed", orphanRemoval = true)
    private List<Image> images = new ArrayList<>();


    @Builder
    public Feed(User user, String content) {
        this.user = user;
        this.content = content;
        this.likeCount = 0;
        this.commentCount = 0;
    }

    public void updateFeed(String content) {
        this.content = content;
    }

    public void addLike() {
        this.likeCount += 1;
    }

    public void removeLike() {
        this.likeCount -= 1;
    }

    public void addComment() {
        this.commentCount += 1;
    }

    public void removeComment() {
        this.commentCount -= 1;
    }
}
