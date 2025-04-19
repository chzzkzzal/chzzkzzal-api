package com.chzzkzzal.core.external.chzzk.application.port;

import com.chzzkzzal.member.dto.ChzzkTokenResponse;

public interface ChzzkAuthPort {
	ChzzkTokenResponse getAccessToken(String code, String state);
}
