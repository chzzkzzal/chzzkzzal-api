package com.chzzkzzal.common.properties;

import java.time.Duration;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.Min;

@ConfigurationProperties(prefix = "chzzkzzal.security.jwt")
public record TokenProperties(
	@NotNull String secretKey,
	@NotNull ExpirationTime expirationTime
) {

	public record ExpirationTime(@Min(0) long accessTokenMinutes, @Min(0) long refreshTokenHours) {
		public Duration accessTokenDuration() {
			return Duration.ofMinutes(accessTokenMinutes);
		}

		public Duration refreshTokenDuration() {
			return Duration.ofHours(refreshTokenHours);
		}
	}
}
