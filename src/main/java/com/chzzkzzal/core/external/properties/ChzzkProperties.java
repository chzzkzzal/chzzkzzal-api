package com.chzzkzzal.core.external.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@ConfigurationProperties(prefix = "chzzk.oauth")
@Getter
public class ChzzkProperties {
	private final String clientId;

	private final String clientSecret;

	public ChzzkProperties(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
}
