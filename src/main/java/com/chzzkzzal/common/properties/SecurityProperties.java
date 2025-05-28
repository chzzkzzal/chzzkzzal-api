package com.chzzkzzal.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "chzzkzzal.security")
public record SecurityProperties(Cookie cookie) {

	public record Cookie(
		String domain,
		boolean httpOnly,
		boolean secure,
		String sameSite) {
	}
}
