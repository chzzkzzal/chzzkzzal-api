package com.chzzkzzal.member.dto;

public record TokenResponse(String accessToken) {
	// JSON 형태: { "accessToken": "..." }
	// 필요하면 refreshToken, expiresIn 등을 추가할 수 있음
}
