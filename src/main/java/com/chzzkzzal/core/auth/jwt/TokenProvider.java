package com.chzzkzzal.core.auth.jwt;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	private final TokenProperties tokenProperties;


	// 예시: 1일
	private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24;

	public String createAccessToken(String channelId) {
		long currentTimeMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeMillis);


		Date expiry = new Date(currentTimeMillis + tokenProperties.expirationTime().accessToken());

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
		Set<SimpleGrantedAuthority> authorities =
			Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(
			new User(claims.getSubject(), "", authorities),
			token,
			authorities);
	}
	private Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith(this.getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.secretKey());
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
