package com.chzzkzzal.zzal.business.application.port.in.query;

import com.chzzkzzal.zzal.business.application.dto.ClientInfo;

public record GetZzalDetailQuery(
	Long zzalId,
	ClientInfo clientInfo
) {
}
