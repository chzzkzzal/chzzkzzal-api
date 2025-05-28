package com.chzzkzzal.zzal.business.application.port.out;

import java.util.Optional;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

public interface SaveZzalPort {
	Optional<Zzal> findById(Long id);

	Zzal save(Zzal post);
}
