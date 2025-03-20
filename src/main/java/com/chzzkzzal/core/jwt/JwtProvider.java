package com.chzzkzzal.core.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	// 예시용 비밀키 (HMAC). 실제론 환경변수나 키관리시스템에서 주입
	private static final String SECRET_KEY = "MY_JWT_SECRET_ABC123";
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
			.setSubject(channelId)    // sub: channelId 등
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	}

	public String createRefreshToken(String channelId) {
		long now = System.currentTimeMillis();
		long expiry = now + (refreshExpirationTime * 1000);

		return Jwts.builder()
			.setSubject(channelId)
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(expiry))
			.signWith(SignatureAlgorithm.HS256, jwtSecretKey)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token);
			return true; // 파싱 성공 시 유효
		} catch (JwtException e) {
			// 유효하지 않으면 예외
			return false;
		}
	}

	public String getChannelIdFromToken(String token) {
		return Jwts.parser()
			.setSigningKey(SECRET_KEY)
			.parseClaimsJws(token)
			.getBody()
			.getSubject(); // sub 필드
	}
}
