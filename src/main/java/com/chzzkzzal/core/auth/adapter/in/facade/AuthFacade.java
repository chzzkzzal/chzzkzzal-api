package com.chzzkzzal.core.auth.adapter.in.facade;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.command.LoginCheckCommand;
import com.chzzkzzal.core.auth.application.command.LogoutCommand;
import com.chzzkzzal.core.auth.application.command.ReissueTokenCommand;
import com.chzzkzzal.core.auth.application.command.SignInCommand;
import com.chzzkzzal.core.auth.application.port.in.CheckLoginStatusUseCase;
import com.chzzkzzal.core.auth.application.port.in.LogoutUseCase;
import com.chzzkzzal.core.auth.application.port.in.ReissueTokenUseCase;
import com.chzzkzzal.core.auth.application.port.in.SignInUseCase;
import com.chzzkzzal.core.auth.application.result.LoginCheckResponse;
import com.chzzkzzal.core.auth.application.result.ReissueTokensResponse;
import com.chzzkzzal.core.auth.application.result.SignInResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacade {
	private final ReissueTokenUseCase reissueTokenUseCase;
	private final LogoutUseCase logoutUseCase;
	private final CheckLoginStatusUseCase checkLoginStatusUseCase;
	private final SignInUseCase signInUseCase;

	public ReissueTokensResponse reissue(ReissueTokenCommand command) {
		return reissueTokenUseCase.execute(command);
	}

	public void logout(LogoutCommand command) {
		logoutUseCase.execute(command);
	}

	public LoginCheckResponse checkLoginStatus(LoginCheckCommand command) {
		return checkLoginStatusUseCase.execute(command);
	}

	public SignInResponse signIn(SignInCommand command) {
		return signInUseCase.execute(command);
	}

}
