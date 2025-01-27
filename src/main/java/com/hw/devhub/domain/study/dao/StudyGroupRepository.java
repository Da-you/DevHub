package com.hw.devhub.domain.study.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hw.devhub.domain.study.domain.StudyGroup;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
}
