package com.chzzkzzal.core.external.chzzk.intrastructure.core;

import static com.chzzkzzal.core.external.chzzk.domain.ChzzkApiFields.*;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.common.properties.ChzzkProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChzzkHttpRequestFactory {

	private final ChzzkProperties props;

	public ChzzkHttpRequest base(final HttpMethod m, final String url) {
		ChzzkHttpRequest req = ChzzkHttpRequest.builder()
			.method(m)
			.url(url)
			.build();

		return req
			.addHeader(CLIENT_API_CLIENT_ID.getDisplayName(), props.getClientId())
			.addHeader(CLIENT_API_CLIENT_SECRET.getDisplayName(), props.getClientSecret())
			.addBody(ACCESS_TOKEN_API_CLIENT_ID.getDisplayName(), props.getClientId())
			.addBody(ACCESS_TOKEN_API_CLIENT_SECRET.getDisplayName(), props.getClientSecret());
	}
}
