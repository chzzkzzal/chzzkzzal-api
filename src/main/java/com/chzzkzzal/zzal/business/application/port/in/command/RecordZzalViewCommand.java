package com.chzzkzzal.zzal.business.application.port.in.command;

import com.chzzkzzal.zzal.business.application.dto.ClientInfo;

// application/port/in/command/RecordZzalViewCommand.java
public record RecordZzalViewCommand(
	Long zzalId,
	Long memberId,
	ClientInfo clientInfo,
	boolean countable
) {
}
