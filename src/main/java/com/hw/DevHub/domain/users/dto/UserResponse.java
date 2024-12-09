package com.hw.DevHub.domain.users.dto;

import com.hw.DevHub.domain.feed.dto.FeedResponse.MypageFeeds;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class UserResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageResponse {

        private String nickname;
        private String profileImagePath;
        private String profileMessage;
        private int feedCount;
        private FollowCountResponse countResponse;
        private List<MypageFeeds> feeds;
    }

    @Getter
    @AllArgsConstructor
    public static class FollowCountResponse {

        private int followerCount;
        private int followingCount;
    }

}
