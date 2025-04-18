package com.chzzkzzal.core.external.chzzk.intrastructure.builder;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.external.properties.ChzzkProperties;

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
