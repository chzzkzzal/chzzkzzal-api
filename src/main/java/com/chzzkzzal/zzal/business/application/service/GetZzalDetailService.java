package com.chzzkzzal.zzal.business.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.business.application.query.MemberInfo;
import com.chzzkzzal.zzal.business.application.event.ZzalViewedEvent;
import com.chzzkzzal.zzal.business.application.port.in.GetZzalDetailUseCase;
import com.chzzkzzal.zzal.business.application.port.in.query.GetZzalDetailQuery;
import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;
import com.chzzkzzal.zzal.business.application.port.out.LoadMemberPort;
import com.chzzkzzal.zzal.business.application.port.out.LoadZzalPort;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.ViewAddable;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;
import com.chzzkzzal.zzal.exception.zzal.ZzalNotFoundException;
import com.chzzkzzal.zzal.infrastructure.event.Events;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetZzalDetailService implements GetZzalDetailUseCase {

	private final LoadMemberPort memberLoader;
	private final LoadZzalPort loadZzalPort;

	public ZzalDetailResponse execute(GetZzalDetailQuery query) {

		Zzal zzal = loadZzalPort.findById(query.zzalId()).orElseThrow(() -> new ZzalNotFoundException());
		Long uploaderId = zzal.getMember().getId();
		MemberInfo member = memberLoader.loadMember(uploaderId);

		Events.raise(
			new ZzalViewedEvent(
				zzal,
				query.zzalId(),
				query.clientInfo(),
				member.id(),
				zzal instanceof ViewAddable
			)
		);
		return ZzalDetailResponse.toResponse(zzal, member);
	}

}
