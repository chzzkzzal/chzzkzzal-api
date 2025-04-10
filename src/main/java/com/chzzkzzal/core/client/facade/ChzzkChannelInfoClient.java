package com.chzzkzzal.core.client.facade;

import static com.chzzkzzal.core.client.ChzzkApiKeyword.*;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.client.callback.ChzzkClientCallbackTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChzzkChannelInfoClient {
	private static final String CHANNEL_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/channels";
	private final ChzzkClientCallbackTemplate template;

	public String fetchChannelInfo(String[] channelIds) {
		return template.callApi(
			HttpMethod.GET, CHANNEL_INFO_URL,
			request -> {
				request.setBodies(CHANNEL_IDS.getDisplayName(), channelIds);
			},
			rawJson -> rawJson);
	}
}
