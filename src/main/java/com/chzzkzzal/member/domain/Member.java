package com.chzzkzzal.member.domain;

import java.time.LocalDateTime;

import com.chzzkzzal.core.BaseTimeEntity;

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

	private String password;

	public Member(LocalDateTime createdAt, LocalDateTime updatedAt, Long id, String channelName, String channelId,
		String password) {
		super(createdAt, updatedAt);
		this.id = id;
		this.channelName = channelName;
		this.channelId = channelId;
		this.password = password;
	}

	public Member(String channelName, String channelId, String password) {
		this.channelName = channelName;
		this.channelId = channelId;
		this.password = password;
	}
}
