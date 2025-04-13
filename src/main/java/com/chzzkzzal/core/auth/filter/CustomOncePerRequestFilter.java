package com.chzzkzzal.core.auth.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOncePerRequestFilter extends OncePerRequestFilter {
	private final AuthenticationFilter authenticationFilter;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String uri = request.getRequestURI();

		// 1) H2-console 요청이면 그냥 통과
		if (uri.startsWith("/h2-console")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 2) /api 등은 인증 없이 통과
		if (uri.startsWith("/api")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 3) '/upload'로 시작하는 요청만 인증 필터를 태움
		if (uri.startsWith("/zzals")) {
			authenticationFilter.doFilterInternal(request, response, filterChain);
			return;
		}

		// 4) 그 외 경로도 인증 없이 통과
		filterChain.doFilter(request, response);

	}
}
