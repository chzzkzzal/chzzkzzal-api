package com.chzzkzzal.core.auth.application.result;

public record SignInResponse(
	String channelName,
	String accessToken,
	String refreshToken
) {
}
