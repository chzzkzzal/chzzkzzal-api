package com.chzzkzzal.zzal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.domain.MemberLoader;
import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.zzal.Zzal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalGetAllServiceImpl implements ZzalGetAllService {
	private final MemberLoader memberLoader;
	private final LoadZzalPort loadZzalPort;

	public List<ZzalDetailResponse> getAll() {
		List<Zzal> zzals = loadZzalPort.findAll();
		List<ZzalDetailResponse> collect = zzals.stream()
			.map(zzal -> ZzalDetailResponse.toResponse(zzal, zzal.getMember()))
			.collect(Collectors.toList());
		return collect;
	}
}
