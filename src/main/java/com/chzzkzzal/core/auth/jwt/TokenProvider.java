package com.chzzkzzal.core.auth.jwt;

import java.nio.charset.StandardCharsets;
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

import com.chzzkzzal.core.auth.domain.MemberUserDetails;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	private final TokenProperties tokenProperties;
	private final MemberRepository memberRepository;


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

		// channelId로 멤버를 리포지토리에서 조회
		Member member = memberRepository.findByChannelId(channelId)
			.orElseThrow(() -> new RuntimeException("Member not found with channelId: " + channelId));

		// 사용자 정의 MemberUserDetails 생성
		MemberUserDetails userDetails = new MemberUserDetails(member);

		Set<SimpleGrantedAuthority> authorities = Collections.singleton(
			new SimpleGrantedAuthority("ROLE_USER"));

		// 표준 User 대신 사용자 정의 userDetails 사용
		return new UsernamePasswordAuthenticationToken(
			userDetails,
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
		byte[] keyBytes = tokenProperties.secretKey().getBytes(StandardCharsets.UTF_8);

		// byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.secretKey64());
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
