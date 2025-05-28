package com.chzzkzzal.core.auth.application.port.in;

import com.chzzkzzal.core.auth.application.command.LoginCheckCommand;
import com.chzzkzzal.core.auth.application.result.LoginCheckResponse;

public interface CheckLoginStatusUseCase {
	LoginCheckResponse execute(LoginCheckCommand command);

}
