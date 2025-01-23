package com.hw.devhub.domain.study.domain;

import java.time.LocalDateTime;

import com.hw.devhub.domain.cafe.domain.Cafe;
import com.hw.devhub.domain.model.BaseTimeEntity;
import com.hw.devhub.domain.users.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyGroup extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
	private User leader;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
	private Cafe cafe;

	@Column(nullable = false)
	private int capacity;
	@Column(nullable = false)
	private int currentCapacity;
	@Column(nullable = false, name = "start_date")
	private LocalDateTime startDateTime;

	@Column(nullable = false, name = "end_date")
	private LocalDateTime endDateTime;

	@Builder
	public StudyGroup(String name, User leader, Cafe cafe, LocalDateTime startDateTime, LocalDateTime endDateTime,
		int capacity) {
		this.name = name;
		this.leader = leader;
		this.cafe = cafe;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.capacity = capacity;
		this.currentCapacity = 1;
	}

	public void addCapacity() {
		this.currentCapacity += 1;
	}
}
