package com.chzzkzzal.zzal.domain.dao;

import java.util.Optional;

import com.chzzkzzal.zzal.domain.model.zzal.Zzal;

public interface DeleteZzalPort {
	Optional<Zzal> findById(Long id);

	void deleteById(Long id);
}
