package com.hw.devhub.domain.study.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyGroupResponse {

	private Long groupId;
	private String groupName;
	private String leader;
	private LocalDateTime createdAt;

	private String profileImagePath;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	private int capacity;
	private int currentCapacity;

	@QueryProjection
	public StudyGroupResponse(Long groupId,String groupName, String leader, LocalDateTime createdAt) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.leader = leader;
		this.createdAt = createdAt;
	}

	// 세부 사항
	@QueryProjection
	public StudyGroupResponse(String groupName, String leader, LocalDateTime createdAt, String profileImagePath,
		LocalDateTime startDateTime, LocalDateTime endDateTime, int capacity, int currentCapacity) {
		this.groupName = groupName;
		this.leader = leader;
		this.createdAt = createdAt;
		this.profileImagePath = profileImagePath;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.capacity = capacity;
		this.currentCapacity = currentCapacity;
	}
}
