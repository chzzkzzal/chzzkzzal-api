package com.chzzkzzal.core.external.chzzk.intrastructure.http.channel;

import static com.chzzkzzal.core.external.chzzk.domain.ChzzkApiFields.*;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.chzzkzzal.core.external.chzzk.domain.model.ChannelInfo;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkClientCallbackTemplate;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkJsonMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChannelHttpClient {
	private static final String CHANNEL_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/channels";
	private final ChzzkClientCallbackTemplate template;
	private final ChzzkJsonMapper jsonHelper;

	public ChannelInfo fetchChannelInfo(String[] channelIds) {
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
			rawJson -> jsonHelper.parseContent(rawJson, ChannelInfo.class)
		);
	}
}
