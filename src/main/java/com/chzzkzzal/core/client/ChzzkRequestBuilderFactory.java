package com.chzzkzzal.core.client;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChzzkRequestBuilderFactory {
	private final ChzzkProperties chzzkProperties;

	public ChzzkRequestBuilder createBuilder(HttpMethod httpMethod, String url) {
		return ChzzkRequestBuilder.initialize(
			httpMethod,
			url,
			chzzkProperties.getClientId(),
			chzzkProperties.getClientSecret()
		);
	}
}
