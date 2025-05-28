package com.chzzkzzal.member.dto;

public record TokenResponse(String accessToken) {
	// JSON 형태: { "accessTokenMinutes": "..." }
	// 필요하면 refreshTokenHours, expiresIn 등을 추가할 수 있음
}
