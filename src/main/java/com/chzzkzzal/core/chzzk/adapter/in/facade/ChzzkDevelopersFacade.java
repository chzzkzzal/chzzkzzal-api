package com.chzzkzzal.core.chzzk.adapter.in.facade;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.chzzk.application.port.in.GetChannelInfoUseCase;
import com.chzzkzzal.core.chzzk.application.port.in.GetUserChannelInfoUseCase;
import com.chzzkzzal.core.chzzk.application.port.in.IssueAccessTokenUseCase;
import com.chzzkzzal.core.chzzk.common.response.ChzzkChannelInfoResponse;
import com.chzzkzzal.core.chzzk.common.response.ChzzkTokenResponse;
import com.chzzkzzal.core.chzzk.common.response.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;

/**
 * Facade → UseCase → Port(out) → Adapter(out) → 치지직 REST 라는 계층 흐름
 */
@Component
@RequiredArgsConstructor
public class ChzzkDevelopersFacade {

	private final IssueAccessTokenUseCase tokenUC;
	private final GetUserChannelInfoUseCase userUC;
	private final GetChannelInfoUseCase channelUC;

	public ChzzkTokenResponse issueAccessToken(String code, String state) {
		return tokenUC.issueAccessToken(code, state);
	}

	public ChzzkUserResponse getUserChannelInfo(String token) {
		return userUC.getUserChannelInfo(token);
	}

	public ChzzkChannelInfoResponse getChannelInfoByIds(String[] ids) {
		return channelUC.getChannelInfoByIds(ids);
	}
}
