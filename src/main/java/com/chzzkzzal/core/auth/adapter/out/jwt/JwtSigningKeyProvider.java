package com.chzzkzzal.core.auth.adapter.out.jwt;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.TokenProperties;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtSigningKeyProvider {

	private final TokenProperties tokenProperties;
	private SecretKey cachedSecretKey;

	@PostConstruct
	private void init() {
		byte[] keyBytes = tokenProperties.secretKey().getBytes(StandardCharsets.UTF_8);
		this.cachedSecretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public SecretKey getSigningKey() {
		return this.cachedSecretKey;
	}
}
