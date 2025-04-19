package com.chzzkzzal.zzal;

import jakarta.servlet.http.HttpServletRequest;

public record ZzalViewedEvent(
	Long zzalId,
	HttpServletRequest httpServletRequest,
	Long memberId
) {
}
