package com.chzzkzzal.zzal.infrastructure.hit;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.application.port.out.CountHitPort;
import com.chzzkzzal.zzal.business.application.port.out.SaveHitPort;
import com.chzzkzzal.zzal.business.domain.hit.ZzalHit;
import com.chzzkzzal.zzal.business.domain.hit.ZzalHitJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZzalHitClientAdapter implements CountHitPort, SaveHitPort {

	private final ZzalHitJpaRepository repository;

	@Override
	public long count(final Long zzalId) {
		return repository.countByZzalId(zzalId);
	}

	@Override
	public void saveIgnoreDuplicate(final ZzalHit hit) {
		if (!repository.existsByUniqueKey(hit.getUniqueKey())) {
			repository.save(hit);
		}
	}
}
