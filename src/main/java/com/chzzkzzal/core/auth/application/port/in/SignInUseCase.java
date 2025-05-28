package com.chzzkzzal.core.auth.application.port.in;

import com.chzzkzzal.core.auth.application.command.SignInCommand;
import com.chzzkzzal.core.auth.application.result.SignInResponse;

public interface SignInUseCase {
	SignInResponse execute(SignInCommand command);
}
