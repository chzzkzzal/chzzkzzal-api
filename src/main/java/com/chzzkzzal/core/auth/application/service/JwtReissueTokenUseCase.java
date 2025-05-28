package com.chzzkzzal.core.auth.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.command.ReissueTokenCommand;
import com.chzzkzzal.core.auth.application.port.in.ReissueTokenUseCase;
import com.chzzkzzal.core.auth.application.port.out.TokenGeneratorPort;
import com.chzzkzzal.core.auth.application.result.ReissueTokensResponse;
import com.chzzkzzal.core.auth.application.result.TokenResult;
import com.chzzkzzal.core.auth.domain.RefreshToken;
import com.chzzkzzal.core.auth.domain.RefreshTokenStorePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtReissueTokenUseCase implements ReissueTokenUseCase {
	private final TokenGeneratorPort tokenGeneratorPort;
	private final RefreshTokenStorePort refreshTokenStorePort;

	@Override
	public ReissueTokensResponse execute(final ReissueTokenCommand command) {
		RefreshToken refreshToken = refreshTokenStorePort.getRefreshTokenByValue(command.refreshToken());
		TokenResult tokenResult = refreshTokenStorePort.rotateRefreshToken(refreshToken);
		String accessToken = tokenGeneratorPort.generateAccessToken(tokenResult.externalId());

		return new ReissueTokensResponse(tokenResult.externalId(), accessToken, tokenResult.refreshToken());
	}

}
