package com.chzzkzzal.zzal.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue("SHORTS")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ShortsZzal extends Zzal {

	private String title;
	private String content;
	private String videoUrl;

	@Override
	public Zzal upload() {
		return new ShortsZzal();

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
