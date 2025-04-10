package com.chzzkzzal.core.client;

import lombok.Getter;

@Getter
public enum ChzzkApiKeyword {

	GRANT_TYPE("grantType"),
	AUTHORIZATION_CODE("authorization_code"),
	CODE("code"),
	STATE("state"),
	AUTHORIZATION("Authorization"),
	BEARER_SPACEBAR("Bearer "),
	CHANNEL_IDS("channelIds"),
	CLIENT_ID("clientId"),
	CLIENT_SECRET("clientSecret");

	String displayName;

	ChzzkApiKeyword(String displayName) {
		this.displayName = displayName;
	}
}
