package com.chzzkzzal.core.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChzzkAPIService {
	private final ChzzkApiClient chzzkApiClient;

	@Value("${chzzk.oauth.client-id}")
	private String clientId;

	@Value("${chzzk.oauth.client-secret}")
	private String clientSecret;

	/**
	 * 치지직 인증 코드로 액세스 토큰 발급
	 */
	public ChzzkTokenResponse getAccessTokenFromChzzk(String code, String state) {
		return chzzkApiClient.fetchAccessToken(clientId, clientSecret, code, state);
	}

	/**
	 * 치지직 토큰 폐기
	 */
	public String revokeToken(String token, String tokenTypeHint) {
		return chzzkApiClient.revokeToken(clientId, clientSecret, token, tokenTypeHint);
	}

	public ChzzkUserResponse getUserInfo(String accessToken) {
		ChzzkUserResponse userDto = chzzkApiClient.fetchUserInfo(accessToken);

		System.out.println(
			"[INFO] signUp user channelId=" + userDto.channelId() + ", channelName=" + userDto.channelName()
		);
		return userDto;
	}

}
