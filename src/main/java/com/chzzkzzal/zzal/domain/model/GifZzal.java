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

@Entity
@DiscriminatorValue("GIF")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GifZzal implements Zzal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gifZzalId;

	private String title;

	@Override
	public Zzal upload() {
		return new GifZzal();
	}

	@Override
	public void bookmark() {

	}

}
