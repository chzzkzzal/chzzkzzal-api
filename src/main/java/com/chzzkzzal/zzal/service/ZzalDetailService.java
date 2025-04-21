package com.chzzkzzal.zzal.service;

import jakarta.servlet.http.HttpServletRequest;

public interface ZzalDetailService {

	ZzalDetailResponse getZZal(Long zzalId, HttpServletRequest request);
}

