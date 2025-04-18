package com.chzzkzzal.core.external.chzzk.intrastructure.http.auth;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkHttpRequestFactory;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkRestExecutor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RevokeTokenHttpClient {
	private static final String CHANNEL_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/token";
	private final ChzzkHttpRequestFactory factory;
	private final ChzzkRestExecutor exec;
	/**
	 * 토큰 폐기
	 */

}
