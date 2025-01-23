package com.hw.devhub.domain.study.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class StudyGroupResponse {

	private String groupName;
	private String leader;
	private LocalDateTime createdAt;

	private String profileImagePath;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	private int capacity;
	private int currentCapacity;

	@Builder // 리스트 조회
	public StudyGroupResponse(String groupName, String leader, LocalDateTime createdAt) {
		this.groupName = groupName;
		this.leader = leader;
		this.createdAt = createdAt;
	}

	@Builder // 세부 사항
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
