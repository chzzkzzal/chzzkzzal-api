package com.chzzkzzal.core.external.chzzk.intrastructure.http.auth;

import static com.chzzkzzal.core.external.chzzk.domain.ChzzkApiFields.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.external.chzzk.application.port.ChzzkAuthPort;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkHttpRequest;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkHttpRequestFactory;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkRestExecutor;
import com.chzzkzzal.member.dto.ChzzkTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessTokenHttpClient implements ChzzkAuthPort {
	private static final String TOKEN_URL = "https://openapi.chzzk.naver.com/auth/v1/token";
	private final ChzzkHttpRequestFactory factory;
	private final ChzzkRestExecutor exec;

	@Override
	public ChzzkTokenResponse getAccessToken(String code, String state) {
		ChzzkHttpRequest req = factory.base(POST, TOKEN_URL)
			.addBody(GRANT_TYPE.getDisplayName(), AUTHORIZATION_CODE.getDisplayName())
			.addBody(CODE.getDisplayName(), code)
			.addBody(STATE.getDisplayName(), state);

		return exec.exchange(req, ChzzkTokenResponse.class);
	}
}
