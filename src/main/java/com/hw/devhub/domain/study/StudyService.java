package com.hw.devhub.domain.study;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hw.devhub.domain.cafe.dao.CafeRepository;
import com.hw.devhub.domain.cafe.domain.Cafe;
import com.hw.devhub.domain.study.dao.StudyMemberRepository;
import com.hw.devhub.domain.study.dao.StudyGroupRepository;
import com.hw.devhub.domain.study.domain.StudyGroup;
import com.hw.devhub.domain.study.domain.StudyMember;
import com.hw.devhub.domain.study.dto.StudyGroupResponse;
import com.hw.devhub.domain.study.dto.StudyGrpupRequest;
import com.hw.devhub.domain.users.dao.UserRepository;
import com.hw.devhub.domain.users.domain.User;
import com.hw.devhub.global.exception.ErrorCode;
import com.hw.devhub.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyService {
	private final StudyGroupRepository studyRepository;
	private final StudyMemberRepository studyMemberRepository;
	private final UserRepository userRepository;
	private final CafeRepository cafeRepository;

	@Transactional
	public void createStudyGroup(Long userId, Long cafeId, StudyGrpupRequest request) {
		User leader = userRepository.findById(userId).orElseThrow(
			() -> new GlobalException(ErrorCode.USER_NOT_FOUND)
		);
		Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(
			() -> new GlobalException(ErrorCode.USER_NOT_FOUND)
		);
		StudyGroup group = StudyGroup.builder()
			.name(request.getName())
			.leader(leader)
			.cafe(cafe)
			.startDateTime(request.getStartDateTime())
			.endDateTime(request.getEndDateTime())
			.capacity(request.getCapacity())
			.build();
		studyRepository.save(group);

		StudyMember member = StudyMember.builder()
			.studyGroup(group)
			.member(leader)
			.build();

		studyMemberRepository.save(member);
	}

	@Transactional
	public void joinGroup(Long userId, Long groupId) {
		User member = userRepository.findById(userId).orElseThrow(
			() -> new GlobalException(ErrorCode.USER_NOT_FOUND)
		);
		StudyGroup study = studyRepository.findById(groupId)
			.orElseThrow(() -> new GlobalException(ErrorCode.FEED_NOT_FOUND));
		studyMemberRepository.save(
			StudyMember.builder()
				.member(member)
				.studyGroup(study)
				.build()
		);
		study.addCapacity();
	}

	@Transactional(readOnly = true)
	public List<StudyGroupResponse> getGroupList() {
		List<StudyGroup> groups = studyRepository.findAll();
		return groups.stream()
			.map(group -> StudyGroupResponse.builder()
				.groupName(group.getName())
				.leader(group.getLeader().getNickname())
				.createdAt(group.getCreatedAt())
				.build()
			)
			.toList();
	}

	@Transactional(readOnly = true)
	public StudyGroupResponse getGroupDetail(Long groupId) {
		StudyGroup group = studyRepository.findById(groupId).orElseThrow(
			() -> new GlobalException(ErrorCode.FEED_NOT_FOUND)
		);
		return StudyGroupResponse.builder()
			.groupName(group.getName())
			.leader(group.getLeader().getNickname())
			.createdAt(group.getCreatedAt())
			.profileImagePath(group.getLeader().getProfileImagePath())
			.startDateTime(group.getStartDateTime())
			.endDateTime(group.getEndDateTime())
			.capacity(group.getCapacity())
			.currentCapacity(group.getCurrentCapacity())
			.build();
	}
}
