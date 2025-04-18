package com.chzzkzzal.core.external.chzzk.intrastructure.http.auth;

import static com.chzzkzzal.core.external.chzzk.domain.ChzzkApiFields.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkClientCallbackTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChzzkRevokeClient {

	private static final String CHANNEL_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/channels";
	private final ChzzkClientCallbackTemplate template;

	/**
	 * 토큰 폐기
	 */
	public String revokeToken(String[] channelIds) {
		return template.callApi(
			POST, CHANNEL_INFO_URL,
			request -> {
				request.setBodies(CHANNEL_IDS.getDisplayName(), channelIds);
			},
			rawJson -> rawJson
		);
	}
}
