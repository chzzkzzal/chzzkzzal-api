package com.chzzkzzal.core.chzzk.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChzzkTokenResponse(
	@JsonProperty("accessToken")
	String accessToken,
	@JsonProperty("refreshToken")
	String refreshToken,
	@JsonProperty("tokenType")
	String tokenType,
	@JsonProperty("expiresIn")
	String expiresIn
) {
}
