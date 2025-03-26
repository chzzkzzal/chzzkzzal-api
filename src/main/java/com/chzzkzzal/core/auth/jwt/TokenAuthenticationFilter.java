package com.chzzkzzal.core.auth.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.filter.AuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter implements AuthenticationFilter {

	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private final TokenProvider tokenProvider;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String token = extractAccessToken(request);

		if (tokenProvider.validateToken(token)) {
			Authentication authentication = tokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}


	private String extractAccessToken(HttpServletRequest request) {
		String authHeader = request.getHeader(HEADER_AUTHORIZATION);

		if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
			return authHeader.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

}
