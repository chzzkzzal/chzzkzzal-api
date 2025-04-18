package com.chzzkzzal.core.external.chzzk.intrastructure.core;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChzzkRestExecutor {

	private final RestTemplate rest;
	private final ChzzkJsonMapper mapper;

	public <T> T exchange(final ChzzkHttpRequest req, final Class<T> type) {
		ResponseEntity<String> res = rest.exchange(
			req.getUrl(),
			req.getMethod(),
			req.toEntity(),
			String.class
		);
		String raw = res.getBody();

		log.debug("[Chzzk] {} {} -> {}", req.getMethod(), req.getUrl(), raw);
		return mapper.parseContent(raw, type);
	}
}
