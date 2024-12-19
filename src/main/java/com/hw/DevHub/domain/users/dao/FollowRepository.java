package com.hw.DevHub.domain.users.dao;

import com.hw.DevHub.domain.users.domain.Follow;
import com.hw.DevHub.domain.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFromUserAndToTarget(User fromUserId, User toUserId);


    int countByToTarget(User toUser);

    int countByFromUser(User fromUser);

    Follow findByFromUserAndToTarget(User fromUser, User toTarget);

    void deleteByFromUserAndToTarget(User fromUser, User toTarget);
}
