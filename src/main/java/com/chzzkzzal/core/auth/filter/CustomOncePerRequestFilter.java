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

		// 1) H2-console 요청은 무조건 통과
		if (uri.startsWith("/h2-console")) {
			filterChain.doFilter(request, response);
			return;
		}

		/* ------------  업로드(POST /api/zzals) 만 인증 ------------ */
		if (uri.startsWith("/api/zzals")                     // 경로 일치
			&& "POST".equalsIgnoreCase(request.getMethod())) {   // 메서드 일치
			authenticationFilter.doFilterInternal(request, response, filterChain);
			return;
		}

		/* ------------- 그 외 /api 는 허용 ----------------------- */
		if (uri.startsWith("/api")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 4) 그 외 경로도 인증 없이 통과
		filterChain.doFilter(request, response);

	}
}
