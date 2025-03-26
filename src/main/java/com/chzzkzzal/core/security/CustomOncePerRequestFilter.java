package com.chzzkzzal.core.security;

import java.io.IOException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@Primary
@RequiredArgsConstructor
public class CustomOncePerRequestFilter extends OncePerRequestFilter {
	private final AuthenticationFilter authenticationFilter;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		authenticationFilter.doFilterInternal(request,response,filterChain);

	}
}
