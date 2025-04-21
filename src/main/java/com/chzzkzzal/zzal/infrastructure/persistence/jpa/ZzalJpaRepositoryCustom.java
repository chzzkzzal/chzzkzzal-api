package com.chzzkzzal.zzal.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.chzzkzzal.zzal.domain.dao.DeleteZzalPort;
import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.dao.SaveZzalPort;
import com.chzzkzzal.zzal.domain.zzal.Zzal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ZzalJpaRepositoryCustom implements SaveZzalPort, LoadZzalPort, DeleteZzalPort {
	private final ZzalJpaRepository jpaRepository;

	@Override
	public void deleteById(Long id) {

	}

	@Override
	public List<Zzal> findAll() {
		return jpaRepository.findAll();
	}

	@Override
	public Optional<Zzal> findById(Long id) {
		return jpaRepository.findById(id);
	}

	@Override
	public Zzal save(Zzal zzal) {
		return jpaRepository.save(zzal);
	}
}
