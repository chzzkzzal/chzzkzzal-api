package com.chzzkzzal.core.external.chzzk.application.port;

import com.chzzkzzal.member.dto.ChzzkUserResponse;

public interface ChzzkUserPort {
	ChzzkUserResponse me(String accessToken);
}
