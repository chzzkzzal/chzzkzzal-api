package com.chzzkzzal.core.client.facade;

import static com.chzzkzzal.core.client.ChzzkApiKeyword.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.client.callback.ChzzkClientCallbackTemplate;
import com.chzzkzzal.core.client.callback.ChzzkJsonHelper;
import com.chzzkzzal.member.dto.ChzzkTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChzzkTokenClient {

	private static final String TOKEN_URL = "https://openapi.chzzk.naver.com/auth/v1/token";
	private final ChzzkClientCallbackTemplate template;
	private final ChzzkJsonHelper jsonHelper;

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
