package com.chzzkzzal.zzal.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberLoader;
import com.chzzkzzal.zzal.Events;
import com.chzzkzzal.zzal.ZzalViewedEvent;
import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.exception.zzal.ZzalNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalDetailServiceImpl implements ZzalDetailService {

	private final MemberLoader memberLoader;
	private final LoadZzalPort loadZzalPort;

	public ZzalDetailResponse getZZal(Long zzalId, HttpServletRequest request) {

		Zzal zzal = loadZzalPort.findById(zzalId).orElseThrow(() -> new ZzalNotFoundException());
		Long uploaderId = zzal.getMember().getId();
		Member member = memberLoader.loadMember(uploaderId);

		Events.raise(new ZzalViewedEvent(zzalId, request, member.getId()));
		return ZzalDetailResponse.toResponse(zzal, member);
	}

}
