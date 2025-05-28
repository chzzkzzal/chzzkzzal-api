package com.chzzkzzal.core.auth.application.port.in;

import com.chzzkzzal.core.auth.application.command.LogoutCommand;

public interface LogoutUseCase {
	void execute(LogoutCommand command);
}
