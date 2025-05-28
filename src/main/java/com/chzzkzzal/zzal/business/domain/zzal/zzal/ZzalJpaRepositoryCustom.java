package com.chzzkzzal.zzal.business.domain.zzal.zzal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.chzzkzzal.zzal.business.application.port.out.DeleteZzalPort;
import com.chzzkzzal.zzal.business.application.port.out.LoadZzalPort;
import com.chzzkzzal.zzal.business.application.port.out.SaveZzalPort;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

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
