package com.chzzkzzal.zzal.business.application.port.in;

import com.chzzkzzal.zzal.business.application.port.in.command.AddHitCommand;

public interface AddHitUseCase {
	String addHit(AddHitCommand cmd);

}
