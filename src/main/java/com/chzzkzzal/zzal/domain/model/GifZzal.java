package com.chzzkzzal.zzal.domain.model;

import com.chzzkzzal.member.domain.Member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue("GIF")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GifZzal extends Zzal {
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private String title;

	@Override
	public Zzal upload() {
		return null;
	}

	@Override
	public void bookmark() {

	}

	@Override
	public void view() {

	}

	@Override
	public int countTotalView() {
		return 0;
	}
}
