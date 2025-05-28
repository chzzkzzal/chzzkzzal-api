package com.chzzkzzal.core.auth.adapter.out.jwt.internal;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.TokenProperties;
import com.chzzkzzal.core.auth.adapter.out.jwt.JwtSigningKeyProvider;
import com.chzzkzzal.core.auth.application.port.out.TokenGeneratorPort;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenGeneratorAdapter implements TokenGeneratorPort {
	private final TokenProperties tokenProperties;
	private final JwtSigningKeyProvider jwtSigningKeyProvider;

	public String generateAccessToken(String externalId) {
		Instant now = Instant.now();
		Instant expiry = now.plus(tokenProperties.expirationTime().accessTokenDuration());

		SecretKey secretKey = jwtSigningKeyProvider.getSigningKey();

		return Jwts.builder()
			.subject(externalId)
			.issuedAt(Date.from(now))
			.expiration(Date.from(expiry))
			.signWith(secretKey)
			.compact();
	}

	public String generateRefreshToken(String externalId) {
		Instant now = Instant.now();
		Instant expiry = now.plus(tokenProperties.expirationTime().refreshTokenDuration());

		SecretKey secretKey = jwtSigningKeyProvider.getSigningKey();

		return Jwts.builder()
			.subject(String.valueOf(externalId))
			.issuedAt(Date.from(now))
			.expiration(Date.from(expiry))
			.signWith(secretKey)
			.compact();
	}
}
