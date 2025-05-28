package com.chzzkzzal.zzal.business.application.port.in;

import com.chzzkzzal.zzal.business.application.port.in.query.GetZzalDetailQuery;
import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;

public interface GetZzalDetailUseCase {

	ZzalDetailResponse execute(GetZzalDetailQuery query);

}

