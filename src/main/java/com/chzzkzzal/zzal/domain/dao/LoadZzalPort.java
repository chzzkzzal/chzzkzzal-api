package com.chzzkzzal.zzal.domain.dao;

import java.util.List;
import java.util.Optional;

import com.chzzkzzal.zzal.domain.model.entity.Zzal;

public interface LoadZzalPort {
	Optional<Zzal> findById(Long id); // 조회 기능

	List<Zzal> findAll(); // 전체 조회 기능 (필요한 경우 추가)
}
