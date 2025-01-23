package com.hw.devhub.domain.study.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyGrpupRequest {

	private String name;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private int capacity;
}
