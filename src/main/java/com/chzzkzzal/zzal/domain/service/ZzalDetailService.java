package com.chzzkzzal.zzal.domain.service;

import jakarta.servlet.http.HttpServletRequest;

public interface ZzalDetailService {

	ZzalDetailResponse getZZal(Long memberId, Long zzalId, HttpServletRequest request);
}

