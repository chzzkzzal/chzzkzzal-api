package com.chzzkzzal.zzal.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberLoader;
import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.model.zzal.Zzal;
import com.chzzkzzal.myviewhistory.MyViewHistoryService;
import com.chzzkzzal.zzal_view_log.ZzalViewLogDto;
import com.chzzkzzal.zzal_view_log.ZzalViewLogService;
import com.chzzkzzal.zzalhits.domain.ZzalHits;
import com.chzzkzzal.zzalhits.service.ZzalHitsService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalDetailServiceImpl implements ZzalDetailService{

	private final MemberLoader memberLoader;
	private final LoadZzalPort loadZzalPort;
	private final ZzalHitsService zzalHitsService;
	private final MyViewHistoryService myViewHistoryService;
	private final ZzalViewLogService zzalViewLogService;

	public ZzalDetailResponse getZZal(Long memberId, Long zzalId, HttpServletRequest request){
		ZzalHits zzalHits = zzalHitsService.addHits(zzalId, request);
		zzalViewLogService.addViewLog(getZzalViewLogDto(memberId, zzalId, zzalHits));
		if (memberId != null) {
			myViewHistoryService.addMemberViewHistory(zzalId, memberId);
		}
		Zzal zzal = loadZzalPort.findById(zzalId).orElseThrow(() -> new EntityNotFoundException("짤이 DB에 없는데용?"));
		Long uploaderId = zzal.getMember().getId();
		Member member = memberLoader.loadMember(uploaderId);
		return ZzalDetailResponse.toResponse(zzal,member);
	}

	private static ZzalViewLogDto getZzalViewLogDto(Long memberId, Long zzalId, ZzalHits zzalHits) {
		return new ZzalViewLogDto(
			zzalId, memberId, zzalHits.getUniqueIdentifier(), zzalHits.getIpAddress(), zzalHits.getUserAgent(),
			zzalHits.getBrowserType(), zzalHits.getDeviceType(),
			LocalDateTime.now());
	}
}
