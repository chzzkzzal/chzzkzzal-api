package com.chzzkzzal.member.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChzzkApiClient {
	private static final String TOKEN_URL = "https://openapi.chzzk.naver.com/auth/v1/token";
	private static final String REVOKE_URL = "https://openapi.chzzk.naver.com/auth/v1/token/revoke";
	private static final String USER_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/users/me";

	private final ObjectMapper objectMapper;
	private final RestTemplate restTemplate;

	/**
	 * 치지직 액세스 토큰 발급 요청
	 */
	public ChzzkTokenResponse fetchAccessToken(String clientId, String clientSecret, String code, String state) {
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("grantType", "authorization_code");
		requestBody.put("clientId", clientId);
		requestBody.put("clientSecret", clientSecret);
		requestBody.put("code", code);
		requestBody.put("state", state);

		String rawJson = sendPostRequest(TOKEN_URL, requestBody);
		System.out.println("[DEBUG] raw JSON = " + rawJson);

		try {
			JsonNode root = objectMapper.readTree(rawJson);
			JsonNode contentNode = root.get("content");
			if (contentNode == null || contentNode.isNull()) {
				throw new RuntimeException("No 'content' field found in the response JSON.");
			}

			ChzzkTokenResponse tokenDto = objectMapper.treeToValue(contentNode, ChzzkTokenResponse.class);
			System.out.println("[DEBUG] tokenDto = " + tokenDto);
			return tokenDto;
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse JSON from Chzzk token response", e);
		}
	}

	/**
	 * 치지직 토큰 폐기 요청
	 */
	public String revokeToken(String clientId, String clientSecret, String token, String tokenTypeHint) {
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("clientId", clientId);
		requestBody.put("clientSecret", clientSecret);
		requestBody.put("token", token);

		String finalHint = (tokenTypeHint == null || tokenTypeHint.isEmpty()) ? "access_token" : tokenTypeHint;
		requestBody.put("tokenTypeHint", finalHint);

		return sendPostRequest(REVOKE_URL, requestBody);
	}

	/**
	 * 치지직 유저 정보 조회 요청
	 */
	public ChzzkUserResponse fetchUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Void> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate
			.exchange(USER_INFO_URL, HttpMethod.GET, entity, String.class);

		String rawJson = response.getBody();
		System.out.println("[DEBUG] user info JSON = " + rawJson);

		try {
			return objectMapper.readValue(rawJson, ChzzkUserResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse user info JSON", e);
		}
	}

	/**
	 * POST 요청 공통 메서드
	 */
	private String sendPostRequest(String url, Map<String, String> requestBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		return response.getBody();
	}
}
