package com.chzzkzzal.zzal.business.application.port.in.command;

import com.chzzkzzal.zzal.business.application.dto.ClientInfo;

public record AddHitCommand(
	Long zzalId,
	ClientInfo clientInfo
) {
}
