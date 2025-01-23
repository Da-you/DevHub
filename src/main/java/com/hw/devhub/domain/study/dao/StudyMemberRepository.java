package com.hw.devhub.domain.study.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hw.devhub.domain.study.domain.StudyMember;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
}
