package com.chzzkzzal.zzal.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ShortsZzal implements Zzal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shortsZzalId;

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
}
