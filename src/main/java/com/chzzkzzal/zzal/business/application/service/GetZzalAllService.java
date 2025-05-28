package com.chzzkzzal.zzal.business.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.business.application.port.in.GetZzalAllUseCase;
import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;
import com.chzzkzzal.zzal.business.application.port.out.LoadZzalPort;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetZzalAllService implements GetZzalAllUseCase {
	private final LoadZzalPort loadZzalPort;

	public List<ZzalDetailResponse> getAll() {
		List<Zzal> zzals = loadZzalPort.findAll();

		return zzals.stream()
			.map(zzal -> ZzalDetailResponse.toResponse(zzal, zzal.getMember()))
			.collect(Collectors.toList());
	}
}
