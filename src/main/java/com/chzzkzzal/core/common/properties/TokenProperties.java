package com.chzzkzzal.core.common.properties;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.Min;

@ConfigurationProperties(prefix = "jwt")
public record TokenProperties(@NotNull String secretKey, @NotNull ExpirationTime expirationTime) {

	public record ExpirationTime(@Min(0) long accessToken, @Min(0) long refreshToken) {
	}
}
