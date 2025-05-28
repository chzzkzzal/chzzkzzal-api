package com.chzzkzzal.core.auth.adapter.in.web.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.adapter.out.jwt.JwtClaimsExtractor;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenAuthenticator {
	private final MemberUserDetailService memberUserDetailService;
	private final JwtClaimsExtractor jwtClaimsExtractor;

	public Authentication authenticate(String token) {
		Claims claims = jwtClaimsExtractor.extractClaims(token);
		String subject = claims.getSubject();
		MemberUserDetails memberUserDetails = memberUserDetailService.loadUserByUsername(subject);

		return new UsernamePasswordAuthenticationToken(
			memberUserDetails, token, memberUserDetails.getAuthorities()
		);
	}
}
