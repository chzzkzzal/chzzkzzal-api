package com.chzzkzzal.core.auth.application.result;

import com.chzzkzzal.common.properties.TokenProperties;

import lombok.Builder;

@Builder
public record AccessTokenResponse(
	String accessToken,
	String expirationTime
) {
	public static AccessTokenResponse of(String accessToken, TokenProperties tokenProperties) {
		return AccessTokenResponse.builder()
			.accessToken(accessToken)
			.expirationTime(String.valueOf(tokenProperties.expirationTime().accessTokenMinutes()))
			.build();
	}
}
