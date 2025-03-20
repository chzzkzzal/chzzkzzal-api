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

	// 예시용 비밀키 (HMAC). 실제론 환경변수나 키관리시스템에서 주입
	private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24; // 1일
	@Value("${jwt.expiration.access}")
	private String accessExpirationTime;

	@Value("${jwt.expiration.refresh}")
	private int refreshExpirationTime;
	@Value("${jwt.secret}")
	private String jwtSecretKey;

	public String createAccessToken(String channelId) {
		// 생성 시 필요한 클레임: 유저 식별자, 만료일
		Date now = new Date();
		Date expiry = new Date(now.getTime() + EXPIRATION_MS);

		return Jwts.builder()
			.setSubject(channelId)
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(
				Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey)),
				SignatureAlgorithm.HS256
			)
			.compact();
	}

	public String createRefreshToken(String channelId) {
		long now = System.currentTimeMillis();
		long expiry = now + (refreshExpirationTime * 1000);

		return Jwts.builder()
			.setSubject(channelId)
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(expiry))
			.signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			// 바뀐 부분
			Jwts.parser()
				.setSigningKey(Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8)))
				.parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	public String getChannelIdFromToken(String token) {
		return Jwts.parser()
			.setSigningKey(jwtSecretKey)
			.parseClaimsJws(token)
			.getBody()
			.getSubject(); // sub 필드
	}
}
