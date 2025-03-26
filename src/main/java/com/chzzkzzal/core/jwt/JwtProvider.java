package com.chzzkzzal.core.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	// 예시: 1일
	private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24;

	@Value("${jwt.expiration.access}")
	private String accessExpirationTime;

	@Value("${jwt.expiration.refresh}")
	private int refreshExpirationTime;

	// Base64로 인코딩된 HMAC 키 문자열 (환경변수/키관리시스템에서 주입)
	@Value("${jwt.secret}")
	private String jwtSecretKey;

	/**
	 * AccessToken 생성
	 */
	public String createAccessToken(String channelId) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + EXPIRATION_MS);

		return Jwts.builder()
			.setSubject(channelId)
			.setIssuedAt(now)
			.setExpiration(expiry)
			// Base64 디코딩 후 signWith
			.signWith(
				Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey)),
				SignatureAlgorithm.HS256
			)
			.compact();
	}

	/**
	 * RefreshToken 생성
	 */
	public String createRefreshToken(String channelId) {
		long now = System.currentTimeMillis();
		long expiry = now + (refreshExpirationTime * 1000L);

		return Jwts.builder()
			.setSubject(channelId)
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(expiry))
			// Base64 디코딩 후 signWith
			.signWith(
				Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey)),
				SignatureAlgorithm.HS256
			)
			.compact();
	}

	/**
	 * 토큰 검증
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				// Base64 디코딩 후 setSigningKey
				.setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey)))
				.parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	/**
	 * 토큰에서 channelId(sub) 추출
	 */
	public String getChannelIdFromToken(String token) {
		return Jwts.parser()
			// Base64 디코딩 후 setSigningKey
			.setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey)))
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
