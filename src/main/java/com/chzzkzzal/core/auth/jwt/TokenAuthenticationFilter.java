package com.chzzkzzal.core.auth.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.filter.AuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter implements AuthenticationFilter {

	private final TokenProvider tokenProvider;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String token = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("SESSION".equals(cookie.getName())) {
					String sessionValue = cookie.getValue();
					token = sessionValue;
					System.out.println("찾았다 쿠키! : " + sessionValue);
					// 세션 값 처리
				}
			}
		}
		System.out.println("Extracted token: " + token); // 토큰이 올바르게 추출되었는지 확인

		if (tokenProvider.validateToken(token)) {
			System.out.println("Token validated successfully"); // 토큰 검증 성공 확인
			Authentication authentication = tokenProvider.getAuthentication(token);
			System.out.println("Authentication principal: " + authentication.getPrincipal()); // 인증 객체의 principal 확인
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			System.out.println("Token validation failed"); // 토큰 검증 실패 확인
		}

		filterChain.doFilter(request, response);
	}

	// private String extractAccessToken(HttpServletRequest request) {
	// 	String authHeader = request.getHeader(HEADER_AUTHORIZATION);
	//
	// 	if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
	// 		return authHeader.substring(TOKEN_PREFIX.length());
	// 	}
	// 	return null;
	// }

}
