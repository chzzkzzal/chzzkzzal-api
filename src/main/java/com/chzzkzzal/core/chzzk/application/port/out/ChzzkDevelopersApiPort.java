package com.chzzkzzal.core.chzzk.application.port.out;

import com.chzzkzzal.core.chzzk.common.response.ChzzkChannelInfoResponse;
import com.chzzkzzal.core.chzzk.common.response.ChzzkTokenResponse;
import com.chzzkzzal.core.chzzk.common.response.ChzzkUserResponse;

public interface ChzzkDevelopersApiPort {
	ChzzkTokenResponse requestAccessToken(String code, String state);

	ChzzkUserResponse requestUserChannelInfo(String accessToken);

	ChzzkChannelInfoResponse requestChannelInfo(String[] ids);
}
