package com.chzzkzzal.zzal.domain.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberLoader;
import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.model.zzal.Zzal;
import com.chzzkzzal.zzal.domain.service.view.ZzalViewHistoryService;
import com.chzzkzzal.zzal.domain.service.view.ZzalViewCountService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalDetailServiceImpl implements ZzalDetailService{

	private final MemberLoader memberLoader;
	private final LoadZzalPort loadZzalPort;
	private final ZzalViewCountService zzalViewCountService;
	private final ZzalViewHistoryService zzalViewHistoryService;

	public ZzalDetailResponse loadDetail(Long memberId, Long zzalId, HttpServletRequest request){
		zzalViewCountService.addViewCount(zzalId,request);
		// memberId가 null이 아닌 경우에만 회원 이력 추가
		if (memberId != null) {
			zzalViewHistoryService.addMemberHistory(zzalId, memberId);
		}
		Zzal zzal = loadZzalPort.findById(zzalId).orElseThrow(() -> new EntityNotFoundException("짤이 DB에 없는데용?"));
		Long uploaderId = zzal.getMember().getId();
		Member member = memberLoader.loadMember(uploaderId);
		return ZzalDetailResponse.toResponse(zzal,member);
	}
}
