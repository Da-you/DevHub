package com.hw.DevHub.domain.image.domain;

import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    @JoinColumn(name = "feed_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;
    private String imageName;
    private String imagePath;
    private int img_index;

    @Builder
    public Image(int img_index, Feed feed, String imageName, String imagePath) {
        this.img_index = img_index;
        this.feed = feed;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
