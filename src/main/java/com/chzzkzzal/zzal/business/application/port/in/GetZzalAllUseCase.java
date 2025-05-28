package com.chzzkzzal.zzal.business.application.port.in;

import java.util.List;

import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;

public interface GetZzalAllUseCase {
	List<ZzalDetailResponse> getAll();
}
