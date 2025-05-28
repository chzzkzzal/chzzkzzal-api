package com.chzzkzzal.core.auth.adapter.out.jwt;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

/**
 * claim : JWT의 Payload에 해당하는 데이터 덩어리
 * 예:
 * {
 *   "sub": "1234567890",
 *   "name": "KJS",
 *   "iat": 1516239022
 * }
 */
@Component
@RequiredArgsConstructor
public class JwtClaimsExtractor {
	private final JwtSigningKeyProvider jwtSigningKeyProvider;

	public Claims extractClaims(String token) {
		SecretKey key = jwtSigningKeyProvider.getSigningKey();
		return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
