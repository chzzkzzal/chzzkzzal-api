package com.chzzkzzal.core.client.callback;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chzzkzzal.core.client.ChzzkRequestBuilder;
import com.chzzkzzal.core.client.ChzzkRequestBuilderFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChzzkClientCallbackTemplate {

	private final RestTemplate restTemplate;
	private final ChzzkRequestBuilderFactory builderFactory;

	/**
	 * @param <T>              응답을 파싱해 만들 최종 DTO 타입
	 * @param httpMethod       GET, POST 등
	 * @param url              요청 URL
	 * @param requestBuilderConsumer  요청을 어떻게 빌드할지(콜백)
	 * @param responseParser         응답 바디(JSON String)를 어떻게 DTO로 바꿀지(콜백)
	 * @return 파싱된 DTO
	 */
	public <T> T callApi(
		HttpMethod httpMethod,
		String url,
		Consumer<ChzzkRequestBuilder> requestBuilderConsumer,
		Function<String, T> responseParser
	) {
		ChzzkRequestBuilder builder = builderFactory.createBuilder(httpMethod, url);
		requestBuilderConsumer.accept(builder);

		HttpEntity<Map<String, String>> requestEntity = builder.toHttpEntity();

		ResponseEntity<String> response = restTemplate.exchange(
			builder.getUrl(),
			builder.getHttpMethod(),
			requestEntity,
			String.class
		);

		String rawJson = response.getBody();
		log.info("[DEBUG] raw JSON = {}", rawJson);

		return responseParser.apply(rawJson);
	}
}
