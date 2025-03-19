package com.chzzkzzal.member.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chzzkzzal.core.jwt.JwtProvider;
import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChzzkAuthService {
	private final ChzzkApiClient chzzkApiClient;
	private final MemberService memberService;
	private final JwtProvider jwtProvider;

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

	public String signin(String channelId, String channelName) {

		Member member = memberService.findOrCreate(channelId, channelName);

		String token = jwtProvider.createAccessToken(member.getChannelId());
		return token;
	}

}
