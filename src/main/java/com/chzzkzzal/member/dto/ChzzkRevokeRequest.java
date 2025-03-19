package com.chzzkzzal.member.dto;

public class ChzzkRevokeRequest {
	private String token;
	private String tokenTypeHint; // "access_token" or "refresh_token"

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenTypeHint() {
		return tokenTypeHint;
	}

	public void setTokenTypeHint(String tokenTypeHint) {
		this.tokenTypeHint = tokenTypeHint;
	}
}
