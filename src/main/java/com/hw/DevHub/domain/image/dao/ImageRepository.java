package com.hw.DevHub.domain.image.dao;

import com.hw.DevHub.domain.feed.domain.Feed;
import com.hw.DevHub.domain.image.domain.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findALlByFeed(Feed feed);

    void deleteAllByFeed(Feed feed);

}
