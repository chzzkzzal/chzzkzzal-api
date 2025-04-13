package com.chzzkzzal.core.client;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChzzkAPIService {
	private final ChzzkApiClient chzzkApiClient;

	/**
	 * 치지직 인증 코드로 액세스 토큰 발급
	 */
	public ChzzkTokenResponse getAccessTokenFromChzzk(String code, String state) {
		return chzzkApiClient.fetchAccessToken(code, state);
	}

	/**
	 * 치지직 토큰 폐기
	 */
	public String revokeToken(String token, String tokenTypeHint) {
		return chzzkApiClient.revokeToken(token, tokenTypeHint);
	}

	public ChzzkUserResponse getUserInfo(String accessToken) {
		ChzzkUserResponse userDto = chzzkApiClient.fetchUserInfo(accessToken);
		return userDto;
	}

	public String getChannelInfos(List<String> channelIds) {
		String[] array = channelIds.toArray(new String[0]);
		return chzzkApiClient.fetchChannelInfo(array);
	}

}
