package com.chzzkzzal.core.chzzk.application.port.in;

import com.chzzkzzal.core.chzzk.common.response.ChzzkTokenResponse;

public interface IssueAccessTokenUseCase {
	ChzzkTokenResponse issueAccessToken(String code, String state);
}
