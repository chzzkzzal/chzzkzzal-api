package com.chzzkzzal.core.auth.application.result;

public record ReissueTokensResponse(
	String externalId,
	String accessToken,
	String refreshToken
) {
}
