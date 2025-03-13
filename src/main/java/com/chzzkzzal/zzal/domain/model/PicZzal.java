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

@DiscriminatorValue("PIC")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PicZzal implements Zzal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long picZzalId;

	@Override
	public Zzal upload() {
		return new PicZzal();
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
