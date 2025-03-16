package com.chzzkzzal.zzal.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
	@Id
	@Column(name = "member_id")
	private Long id;

	public void upload(Zzal zzal) {
		zzal.upload();
	}

}
