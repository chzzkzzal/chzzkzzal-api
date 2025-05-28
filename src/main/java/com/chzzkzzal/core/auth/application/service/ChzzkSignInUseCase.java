package com.chzzkzzal.core.auth.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.command.SignInCommand;
import com.chzzkzzal.core.auth.application.port.in.SignInUseCase;
import com.chzzkzzal.core.auth.application.port.out.SaveMemberCommandPort;
import com.chzzkzzal.core.auth.application.port.out.TokenGeneratorPort;
import com.chzzkzzal.core.auth.application.result.SignInResponse;
import com.chzzkzzal.core.auth.domain.RefreshToken;
import com.chzzkzzal.core.auth.domain.RefreshTokenStorePort;
import com.chzzkzzal.member.business.application.query.MemberInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChzzkSignInUseCase implements SignInUseCase {
	private final SaveMemberCommandPort saveMemberCommandPort;
	private final RefreshTokenStorePort refreshTokenStorePort;
	private final TokenGeneratorPort tokenGeneratorPort;

	@Override
	public SignInResponse execute(final SignInCommand command) {
		MemberInfo memberInfo = saveMemberCommandPort.saveIfNotExist(command.channelId(), command.channelName());
		String accessToken = tokenGeneratorPort.generateAccessToken(String.valueOf(memberInfo.id()));
		String refreshToken = tokenGeneratorPort.generateRefreshToken(String.valueOf(memberInfo.id()));
		refreshTokenStorePort.save(RefreshToken.of(String.valueOf(memberInfo.id()), refreshToken));

		return new SignInResponse(memberInfo.channelName(), accessToken, refreshToken);
	}
}
