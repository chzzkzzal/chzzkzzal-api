package com.chzzkzzal.zzal.domain.model;

import java.time.LocalDateTime;

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

	private String name;
	private String email;

	private String password;

	public Member(LocalDateTime createdAt, LocalDateTime updatedAt, Long id, String name, String email,
		String password) {
		super(createdAt, updatedAt);
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Member(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
