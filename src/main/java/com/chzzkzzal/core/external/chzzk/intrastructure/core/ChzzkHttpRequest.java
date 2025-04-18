package com.chzzkzzal.core.external.chzzk.intrastructure.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ChzzkHttpRequest {

	private final HttpMethod method;
	private final String url;
	@Builder.Default
	private final Map<String, String> body = new HashMap<>();
	@Builder.Default
	private final HttpHeaders headers = new HttpHeaders();

	public ChzzkHttpRequest addHeader(final String k, final String v) {
		this.headers.add(k, v);
		return this;
	}

	public ChzzkHttpRequest addBody(final String k, final String v) {
		this.body.put(k, v);
		return this;
	}

	public HttpEntity<Map<String, String>> toEntity() {
		return new HttpEntity<>(body, headers);
	}
}
