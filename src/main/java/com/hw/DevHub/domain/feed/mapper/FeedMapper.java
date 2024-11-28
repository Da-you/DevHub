package com.hw.DevHub.domain.feed.mapper;

import com.hw.DevHub.domain.feed.domain.Feed;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedMapper {

    void insertFeed(Feed feed);
    List<Feed> getFeeds();
    Feed getFeedById(Long feedId);
    void deleteFeedById(Long feedId);
    void updateFeed(Long feedId, String content);
}
