package com.chzzkzzal.zzal.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.chzzkzzal.zzal.domain.dao.DeleteZzalPort;
import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.dao.SaveZzalPort;
import com.chzzkzzal.zzal.domain.model.Zzal;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ZzalJpaRepositoryImpl implements DeleteZzalPort, LoadZzalPort, SaveZzalPort {
	private final ZzalJpaRepository jpaRepository;

	@Override
	public Optional<Zzal> findById(Long zzalId) {
		Optional<Zzal> zzal = jpaRepository.findById(zzalId);
		return zzal;
	}

	@Override
	public void save(Zzal zzal) {
		jpaRepository.save(zzal);
	}

	@Override
	public List<Zzal> findAll() {
		return jpaRepository.findAll();
	}

	@Override
	public void deleteById(Long zzalId) {
		jpaRepository.deleteById(zzalId);
	}
}
