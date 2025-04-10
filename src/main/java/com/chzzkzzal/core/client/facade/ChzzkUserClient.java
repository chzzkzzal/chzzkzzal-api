package com.chzzkzzal.core.client.facade;

import static com.chzzkzzal.core.client.ChzzkApiKeyword.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.client.callback.ChzzkClientCallbackTemplate;
import com.chzzkzzal.core.client.callback.ChzzkJsonHelper;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChzzkUserClient {

	private static final String USER_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/users/me";
	private final ChzzkClientCallbackTemplate template;
	private final ChzzkJsonHelper jsonHelper;

	/**
	 * 유저 정보 조회
	 */
	public ChzzkUserResponse fetchUserInfo(String accessToken) {
		return template.callApi(
			GET, USER_INFO_URL,
			request -> {
				request.setHeader(AUTHORIZATION_CODE.getDisplayName(), BEARER_SPACEBAR.getDisplayName() + accessToken);
			},
			rawJson -> jsonHelper.parseContent(rawJson, ChzzkUserResponse.class)
		);

	}
}
