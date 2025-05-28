package com.chzzkzzal.core.auth.application.port.in;

import com.chzzkzzal.core.auth.application.command.ReissueTokenCommand;
import com.chzzkzzal.core.auth.application.result.ReissueTokensResponse;

public interface ReissueTokenUseCase {
	ReissueTokensResponse execute(ReissueTokenCommand command);
}
