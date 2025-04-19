package com.chzzkzzal.zzal.domain.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberLoader;
import com.chzzkzzal.zzal.Events;
import com.chzzkzzal.zzal.ZzalViewedEvent;
import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.model.zzal.Zzal;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalDetailServiceImpl implements ZzalDetailService {

	private final MemberLoader memberLoader;
	private final LoadZzalPort loadZzalPort;

	public ZzalDetailResponse getZZal(Long zzalId, HttpServletRequest request) {

		// 1.짤 조회 하고
		Zzal zzal = loadZzalPort.findById(zzalId).orElseThrow(() -> new EntityNotFoundException("짤이 DB에 없는데용?"));
		Long uploaderId = zzal.getMember().getId();
		Member member = memberLoader.loadMember(uploaderId);
		// 짤 조회했다는 이벤트를 만들어(DTO) 그리고 이벤트 만들었다고 알려야해. raise 야해.
		Events.raise(new ZzalViewedEvent(zzalId, request, member.getId()));
		//
		return ZzalDetailResponse.toResponse(zzal, member);
	}

}
