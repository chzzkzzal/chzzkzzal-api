package com.chzzkzzal.core.chzzk.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.chzzk.application.port.in.IssueAccessTokenUseCase;
import com.chzzkzzal.core.chzzk.application.port.out.ChzzkDevelopersApiPort;
import com.chzzkzzal.core.chzzk.common.response.ChzzkTokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueAccessTokenService implements IssueAccessTokenUseCase {
	private final ChzzkDevelopersApiPort api;

	@Override
	public ChzzkTokenResponse issueAccessToken(String code, String state) {
		return api.requestAccessToken(code, state);
	}
}
