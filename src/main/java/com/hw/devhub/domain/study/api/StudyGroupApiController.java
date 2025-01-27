package com.hw.devhub.domain.study.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw.devhub.domain.study.StudyService;
import com.hw.devhub.domain.study.dto.StudyGroupResponse;
import com.hw.devhub.domain.study.dto.StudyGrpupRequest;
import com.hw.devhub.global.annotation.CurrentUser;
import com.hw.devhub.global.annotation.LoginRequired;
import com.hw.devhub.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyGroupApiController {

	private final StudyService studyService;

	@LoginRequired
	@PostMapping
	public void createStudyGroup(@CurrentUser Long userId, @PathVariable Long cafeId, @RequestBody
	StudyGrpupRequest request) {
		studyService.createStudyGroup(userId, cafeId, request);
	}

	@LoginRequired
	@PostMapping("/{groupId}")
	public void joinGroup(@CurrentUser Long userId, @PathVariable Long groupId) {
		studyService.joinGroup(userId, groupId);
	}

	@GetMapping
	public ApiResponse<List<StudyGroupResponse>> getGroupList() {
		return ApiResponse.success(studyService.getGroupList());
	}

	@GetMapping("/{groupId}")
	public ApiResponse<StudyGroupResponse> getGroupDetail(@PathVariable Long groupId) {
		return ApiResponse.success(studyService.getGroupDetail(groupId));
	}
}
