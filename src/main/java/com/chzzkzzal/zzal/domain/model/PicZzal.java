package com.chzzkzzal.zzal.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue("PIC")
@Entity
@NoArgsConstructor
@Builder
@Getter
public class PicZzal extends Zzal {

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
