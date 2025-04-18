package com.chzzkzzal.core.security.infrastructure.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.security.domain.MemberUserDetailService;
import com.chzzkzzal.core.security.properties.TokenProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	private final TokenProperties tokenProperties;
	private final MemberUserDetailService memberUserDetailService;

	// 예시: 1일
	private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24;

	public String createAccessToken(String channelId) {
		long currentTimeMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeMillis);

		Date expiry = new Date(currentTimeMillis + tokenProperties.expirationTime().accessToken() * 1000);

		SecretKey secretKey = getSigningKey();

		return Jwts.builder()
			.setSubject(channelId)
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(secretKey)
			.compact();
	}

	public String generateRefreshToken(String externalId) {
		long currentTimeMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeMillis);
		SecretKey secretKey = getSigningKey();

		return Jwts.builder()
			.subject(String.valueOf(externalId))
			.issuedAt(now)
			.signWith(secretKey)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		String channelId = claims.getSubject();

		UserDetails userDetails = memberUserDetailService.loadUserByUsername(channelId);
		return new UsernamePasswordAuthenticationToken(
			userDetails,
			token,
			userDetails.getAuthorities()
		);
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith(this.getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = tokenProperties.secretKey().getBytes(StandardCharsets.UTF_8);

		// byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.secretKey64());
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
