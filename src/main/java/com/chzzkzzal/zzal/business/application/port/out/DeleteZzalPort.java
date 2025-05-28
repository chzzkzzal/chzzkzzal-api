package com.chzzkzzal.zzal.business.application.port.out;

import java.util.Optional;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

public interface DeleteZzalPort {
	Optional<Zzal> findById(Long id);

	void deleteById(Long id);
}
