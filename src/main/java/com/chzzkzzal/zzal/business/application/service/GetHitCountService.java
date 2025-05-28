package com.chzzkzzal.zzal.business.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.zzal.business.application.port.in.GetHitCountUseCase;
import com.chzzkzzal.zzal.business.application.port.out.CountHitPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetHitCountService implements GetHitCountUseCase {
	private final CountHitPort countPort;

	@Override
	public long count(final Long zzalId) {
		return countPort.count(zzalId);
	}
}
