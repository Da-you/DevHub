package com.hw.DevHub.domain.users.mapper;

import com.hw.DevHub.domain.users.domain.Follow;
import com.hw.DevHub.domain.users.dto.UserResponse.FollowCountResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowMapper {

    void insertFollower(Follow relationship);

    void deleteFollower(@Param("fromUserId") Long userId, @Param("toTargetId") Long targetId);

    // 파라미터가 2개 이상일 경우 파라미터 클래스를 별도로 정의하거나, 맵을 사용 또는 @Param 을 통해서 이름을 명시할것
    boolean existsFollower(@Param("fromUserId") Long from_user_id,
        @Param("toTargetId") Long to_target_id);

    FollowCountResponse followCount(Long userId);


}
