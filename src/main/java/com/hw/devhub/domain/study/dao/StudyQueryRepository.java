package com.hw.devhub.domain.study.dao;

import static com.hw.devhub.domain.study.domain.QStudyGroup.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hw.devhub.domain.study.domain.QStudyGroup;
import com.hw.devhub.domain.study.dto.QStudyGroupResponse;
import com.hw.devhub.domain.study.dto.StudyGroupResponse;
import com.hw.devhub.domain.users.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyQueryRepository {

	private final JPAQueryFactory queryFactory;

	// 모든 스터디를 반환하는 메서드
	public List<StudyGroupResponse> findAllGroups() {
		QUser leader = QUser.user;
		QStudyGroup group = studyGroup;
		return queryFactory.select(new QStudyGroupResponse(
					group.id,
					group.name,
					leader.nickname,
					group.createdAt
				)
			)
			.fetch();
	}

	// 단건
	public StudyGroupResponse getGroupDetailByGroupId(Long groupId) {
		QUser leader = QUser.user;
		QStudyGroup group = studyGroup;
		return queryFactory.select(new QStudyGroupResponse(
					group.name,
					leader.nickname,
					group.createdAt,
					leader.profileImagePath,
					group.startDateTime,
					group.endDateTime,
					group.capacity,
					group.currentCapacity
				)
			)
			.where(group.id.eq(groupId))
			.fetchOne();
	}
	// 키워드
}
