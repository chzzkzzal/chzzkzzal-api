package com.chzzkzzal.core.external.chzzk.domain;

import lombok.Getter;

@Getter
public enum ChzzkApiFields {

	GRANT_TYPE("grantType"),
	AUTHORIZATION_CODE("authorization_code"),
	CODE("code"),
	STATE("state"),
	AUTHORIZATION("Authorization"),
	BEARER_SPACEBAR("Bearer "),
	CHANNEL_IDS("channelIds"),
	ACCESS_TOKEN_API_CLIENT_ID("clientId"),
	ACCESS_TOKEN_API_CLIENT_SECRET("clientSecret"),
	CLIENT_API_CLIENT_ID("Client-Id"),
	CLIENT_API_CLIENT_SECRET("Client-Secret");

	String displayName;

	ChzzkApiFields(String displayName) {
		this.displayName = displayName;
	}
}
