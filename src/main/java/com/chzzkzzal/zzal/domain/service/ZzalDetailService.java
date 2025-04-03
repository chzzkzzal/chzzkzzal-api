package com.chzzkzzal.zzal.domain.service;

import com.chzzkzzal.zzal.domain.model.entity.Zzal;

public interface ZzalDetailService {

	ZzalDetailResponse loadDetail(Long memberId, Long zzalId);
}

