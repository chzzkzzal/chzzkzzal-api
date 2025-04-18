package com.chzzkzzal.member.domain;

import java.time.LocalDateTime;

import com.chzzkzzal.core.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	private String channelName;
	private String channelId;

	public Member(LocalDateTime createdAt, LocalDateTime updatedAt, Long id, String channelName, String channelId) {
		super(createdAt, updatedAt);
		this.id = id;
		this.channelName = channelName;
		this.channelId = channelId;
	}

	public Member(String channelName, String channelId) {
		this.channelName = channelName;
		this.channelId = channelId;
	}
}
