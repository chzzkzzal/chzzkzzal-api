package com.chzzkzzal.core.auth.application.result;

public record TokenResult(
	String refreshToken,
	String externalId
) {
}
