package com.chzzkzzal.core.client;

import static org.springframework.http.HttpMethod.*;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChzzkApiClient {
	private static final String TOKEN_URL = "https://openapi.chzzk.naver.com/auth/v1/token";
	private static final String REVOKE_URL = "https://openapi.chzzk.naver.com/auth/v1/token/revoke";
	private static final String USER_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/users/me";
	private static final String CHANNEL_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/channels";

	private final ObjectMapper objectMapper;
	private final RestTemplate restTemplate;
	private final ChzzkRequestBuilderFactory chzzkRequestBuilderFactory;

	/**
	 * 치지직 액세스 토큰 발급 요청
	 */
	public ChzzkTokenResponse fetchAccessToken(String code, String state) {
		ChzzkRequestBuilder request = chzzkRequestBuilderFactory.createBuilder(POST, TOKEN_URL);
		ChzzkApiStrategy<HttpEntity<Map<String, String>>> strategy = () -> {
			request.setBody("grantType", "authorization_code");
			request.setBody("code", code);
			request.setBody("state", state);
			return request.toHttpEntity();
		};

		HttpEntity<Map<String, String>> entity = strategy.callback();

		ChzzkResponseBuilder<ChzzkTokenResponse> strategy2 = () -> {
			ResponseEntity<String> response = restTemplate.postForEntity(request.getUrl(), entity, String.class);
			String rawJson = response.getBody();
			log.info("[DEBUG] raw JSON = {}", rawJson);

			try {
				JsonNode contentNode = objectMapper.readTree(rawJson).get("content");
				validateNull(contentNode);

				ChzzkTokenResponse tokenDto = objectMapper.treeToValue(contentNode, ChzzkTokenResponse.class);
				log.info("[DEBUG] tokenDto = {}", tokenDto);
				return tokenDto;
			} catch (JsonProcessingException e) {
				throw new RuntimeException("Failed to parse JSON from Chzzk token response", e);
			}
		};
		ChzzkTokenResponse callback = strategy2.callback();
		return callback;

	}

	private void validateNull(JsonNode contentNode) {
		if (contentNode == null || contentNode.isNull()) {
			throw new RuntimeException("No 'content' field found in the response JSON.");
		}
	}

	/**
	 * 치지직 토큰 폐기 요청
	 */
	public String revokeToken(String token, String tokenTypeHint) {
		ChzzkRequestBuilder request = chzzkRequestBuilderFactory.createBuilder(POST, REVOKE_URL);

		ChzzkApiStrategy<HttpEntity<Map<String, String>>> strategy = () -> {
			request.setBody("token", token);
			String finalHint = (tokenTypeHint == null || tokenTypeHint.isEmpty()) ? "access_token" : tokenTypeHint;
			request.setBody("tokenTypeHint", finalHint);
			return request.toHttpEntity();
		};

		HttpEntity<Map<String, String>> entity = strategy.callback();

		ChzzkResponseBuilder<String> strategy2 = () -> {
			ResponseEntity<String> response = restTemplate.postForEntity(request.getUrl(), entity, String.class);
			return response.getBody();
		};
		String callback = strategy2.callback();
		return callback;
	}

	/**
	 * 치지직 유저 정보 조회 요청
	 */
	public ChzzkUserResponse fetchUserInfo(String accessToken) {
		ChzzkRequestBuilder request = chzzkRequestBuilderFactory.createBuilder(GET, USER_INFO_URL);

		ChzzkApiStrategy<HttpEntity<Map<String, String>>> strategy = () -> {
			request.setHeader("Authorization", "Bearer " + accessToken);
			return request.toHttpEntity();
		};
		HttpEntity<Map<String, String>> entity = strategy.callback();

		ChzzkResponseBuilder<ChzzkUserResponse> strategy2 = () -> {
			ResponseEntity<String> response = restTemplate.exchange(request.getUrl(), request.getHttpMethod(), entity,
				String.class);

			String rawJson = response.getBody();
			log.info("[DEBUG] user info JSON = {}", rawJson);

			try {
				JsonNode root = objectMapper.readTree(rawJson);
				JsonNode contentNode = root.get("content");
				return objectMapper.treeToValue(contentNode, ChzzkUserResponse.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException("Failed to parse user info JSON", e);
			}
		};
		ChzzkUserResponse callback = strategy2.callback();
		return callback;

	}

	public String fetchChannelInfo(String[] channelIds) {
		// GET 요청인 경우, query parameter로 channelIds를 전달
		// 각각의 채널 ID를 반복 쿼리 파라미터로 붙입니다.
		StringBuilder query = new StringBuilder();
		for (String id : channelIds) {
			query.append("channelIds=").append(id).append("&");
		}
		// 마지막 & 제거
		if (query.length() > 0) {
			query.setLength(query.length() - 1);
		}
		String urlWithQuery = CHANNEL_INFO_URL + "?" + query;
		log.info("[DEBUG] fetchChannelInfo URL: {}", urlWithQuery);
		ResponseEntity<String> response = restTemplate.exchange(urlWithQuery, GET, null, String.class);
		String rawJson = response.getBody();
		log.info("[DEBUG] channel info JSON = {}", rawJson);
		return rawJson;
	}

}
