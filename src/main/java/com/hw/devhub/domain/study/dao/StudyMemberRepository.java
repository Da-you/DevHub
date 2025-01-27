package com.hw.devhub.domain.study.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hw.devhub.domain.study.domain.StudyMember;
import com.hw.devhub.domain.users.domain.User;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
	boolean getByMember(User member);
}
