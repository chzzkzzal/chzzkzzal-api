package com.chzzkzzal.core.auth.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.command.LogoutCommand;
import com.chzzkzzal.core.auth.application.port.in.LogoutUseCase;
import com.chzzkzzal.core.auth.domain.RefreshTokenStorePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtLogoutUseCase implements LogoutUseCase {
	private final RefreshTokenStorePort refreshTokenStorePort;

	@Override
	public void execute(LogoutCommand command) {
		refreshTokenStorePort.expireRefreshToken(command.refreshToken());
	}
}
