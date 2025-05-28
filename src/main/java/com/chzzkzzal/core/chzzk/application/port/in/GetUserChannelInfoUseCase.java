package com.chzzkzzal.core.chzzk.application.port.in;

import com.chzzkzzal.core.chzzk.common.response.ChzzkUserResponse;

public interface GetUserChannelInfoUseCase {
	ChzzkUserResponse getUserChannelInfo(String accessToken);
}
