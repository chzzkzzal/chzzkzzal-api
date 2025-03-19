package com.chzzkzzal.core.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private String secret;
	private Expiration expiration = new Expiration();

	@Getter
	@Setter
	public static class Expiration {
		private long access;   // Access Token 유효기간(초)
		private long refresh;  // Refresh Token 유효기간(초)
	}
}
