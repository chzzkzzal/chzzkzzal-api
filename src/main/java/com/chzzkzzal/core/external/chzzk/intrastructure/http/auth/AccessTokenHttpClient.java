package com.chzzkzzal.core.external.chzzk.intrastructure.http.auth;

import static com.chzzkzzal.core.external.chzzk.domain.ChzzkApiFields.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.external.chzzk.application.port.ChzzkAuthPort;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkClientCallbackTemplate;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkJsonMapper;
import com.chzzkzzal.member.dto.ChzzkTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessTokenHttpClient implements ChzzkAuthPort {

	private static final String TOKEN_URL = "https://openapi.chzzk.naver.com/auth/v1/token";
	private final ChzzkClientCallbackTemplate template;
	private final ChzzkJsonMapper jsonHelper;

	/**
	 * 액세스 토큰 발급 (콜백으로 구체 로직 정의)
	 */
	public ChzzkTokenResponse fetchAccessToken(String code, String state) {
		return template.callApi(
			POST, TOKEN_URL,
			request -> {
				request.setBody(GRANT_TYPE.getDisplayName(), AUTHORIZATION_CODE.getDisplayName());
				request.setBody(CODE.getDisplayName(), code);
				request.setBody(STATE.getDisplayName(), state);
			},
			rawJson -> jsonHelper.parseContent(rawJson, ChzzkTokenResponse.class)
		);
	}
}
