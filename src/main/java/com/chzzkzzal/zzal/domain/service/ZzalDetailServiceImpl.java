package com.chzzkzzal.zzal.domain.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberLoader;
import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.model.zzal.Zzal;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalDetailServiceImpl implements ZzalDetailService{

	private final MemberLoader memberLoader;
	private final LoadZzalPort loadZzalPort;

	public ZzalDetailResponse loadDetail(Long memberId, Long zzalId){
		Member member = memberLoader.loadMember(memberId);
		Zzal zzal = loadZzalPort.findById(zzalId).orElseThrow(() -> new EntityNotFoundException("짤이 DB에 없는데용?"));
		return ZzalDetailResponse.toResponse(zzal,member);
	}
}
