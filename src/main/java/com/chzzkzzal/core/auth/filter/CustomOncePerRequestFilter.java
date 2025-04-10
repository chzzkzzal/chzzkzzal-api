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
		// 1) H2-console 요청이면 그냥 통과
		String uri = request.getRequestURI();
		if (uri.startsWith("/h2-console")) {
			filterChain.doFilter(request, response);
			return;
		}
		if (uri.startsWith("/api/**")) {
			filterChain.doFilter(request, response);
			return;
		}
		if (uri.startsWith("")) {
			filterChain.doFilter(request, response);
			return;
		}
		authenticationFilter.doFilterInternal(request, response, filterChain);

	}
}
