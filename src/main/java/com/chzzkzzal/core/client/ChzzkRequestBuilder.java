package com.chzzkzzal.core.client;

import static com.chzzkzzal.core.client.ChzzkApiKeyword.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class ChzzkRequestBuilder {
	private final String clientId;

	private final String clientSecret;

	private final HttpMethod httpMethod;
	private final String url;
	@NotNull
	private final Map<String, String> requestBody;

	@NotNull
	private final HttpHeaders headers;

	@Builder
	public ChzzkRequestBuilder(HttpMethod httpMethod, String url, String clientId, String clientSecret) {
		this.httpMethod = httpMethod;
		this.url = url;
		this.requestBody = new HashMap<>();
		this.headers = new HttpHeaders();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public static ChzzkRequestBuilder initialize(
		HttpMethod httpMethod, String url,
		String clientId,
		String clientSecret
	) {

		ChzzkRequestBuilder request = ChzzkRequestBuilder.builder()
			.httpMethod(httpMethod)
			.url(url)
			.clientId(clientId)
			.clientSecret(clientSecret)
			.build();

		initializeHeader(request.getHeaders());
		initializeBody(request.getRequestBody(), clientId, clientSecret);

		return request;
	}

	private static void initializeHeader(HttpHeaders headers) {
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	private static void initializeBody(Map<String, String> requestBody, String clientId, String clientSecret) {
		requestBody.put(CLIENT_ID.getDisplayName(), clientId);
		requestBody.put(CLIENT_SECRET.getDisplayName(), clientSecret);
	}

	public void setBody(String key, String value) {
		this.requestBody.put(key, value);
	}

	public void setBodies(String key, String[] values) {
		this.requestBody.put(key, String.join(",", values));
	}

	public void setHeader(String key, String value) {
		this.headers.add(key, value);
	}

	public HttpEntity<Map<String, String>> toHttpEntity() {
		return new HttpEntity<>(requestBody, headers);
	}
}
