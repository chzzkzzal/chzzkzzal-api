package com.chzzkzzal.core.auth.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.command.LoginCheckCommand;
import com.chzzkzzal.core.auth.application.port.in.CheckLoginStatusUseCase;
import com.chzzkzzal.core.auth.application.port.out.TokenValidatorPort;
import com.chzzkzzal.core.auth.application.result.LoginCheckResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtCheckLoginStatusUseCase implements CheckLoginStatusUseCase {
	private final TokenValidatorPort tokenValidatorPort;

	@Override
	public LoginCheckResponse execute(final LoginCheckCommand command) {
		boolean isAuthenticated = tokenValidatorPort.isValid(command.accessToken());
		log.info("로그인 상태 : {}", isAuthenticated);

		return new LoginCheckResponse(isAuthenticated);
	}

}

