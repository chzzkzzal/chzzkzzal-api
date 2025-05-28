package com.chzzkzzal.zzal.business.application.event;

import com.chzzkzzal.zzal.business.application.dto.ClientInfo;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

public record ZzalViewedEvent(
	Zzal zzal,
	Long zzalId,
	ClientInfo clientInfo,
	Long memberId,
	boolean countable

) {
}
