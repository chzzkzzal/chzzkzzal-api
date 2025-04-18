package com.chzzkzzal.core.external.chzzk.intrastructure.http.user;

import static com.chzzkzzal.core.external.chzzk.domain.ChzzkApiFields.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.external.chzzk.application.port.ChzzkUserPort;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkClientCallbackTemplate;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkJsonMapper;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChzzkUserHttpClient implements ChzzkUserPort {

	private static final String USER_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/users/me";
	private final ChzzkClientCallbackTemplate template;
	private final ChzzkJsonMapper jsonHelper;

	/**
	 * 유저 정보 조회
	 */
	public ChzzkUserResponse fetchUserInfo(String accessToken) {
		return template.callApi(
			GET, USER_INFO_URL,
			request -> {
				request.setHeader(AUTHORIZATION.getDisplayName(), BEARER_SPACEBAR.getDisplayName() + accessToken);
			},
			rawJson -> jsonHelper.parseContent(rawJson, ChzzkUserResponse.class)
		);

	}
}
