package com.chzzkzzal.core.client.facade;

import static com.chzzkzzal.core.client.ChzzkApiKeyword.*;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.chzzkzzal.core.client.callback.ChzzkClientCallbackTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChzzkChannelInfoClient {
	private static final String CHANNEL_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/channels";
	private final ChzzkClientCallbackTemplate template;

	public String fetchChannelInfo(String[] channelIds) {
		// 1. 채널 ID 배열을 합쳐서 쿼리파라미터로 만들기
		String joinedChannelIds = String.join(",", channelIds);

		// 2. 기존 CHANNEL_INFO_URL에 쿼리파라미터를 붙인 URL 생성
		String urlWithParams = UriComponentsBuilder
			.fromHttpUrl(CHANNEL_INFO_URL)
			.queryParam(CHANNEL_IDS.getDisplayName(), joinedChannelIds)
			.build()
			.toString();

		return template.callApi(
			HttpMethod.GET,
			urlWithParams,
			request -> {
			},
			rawJson -> rawJson
		);
	}
}
