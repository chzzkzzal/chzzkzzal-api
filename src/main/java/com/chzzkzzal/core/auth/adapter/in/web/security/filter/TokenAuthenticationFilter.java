package com.chzzkzzal.core.auth.adapter.in.web.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.adapter.in.web.exception.MissingJwtTokenException;
import com.chzzkzzal.core.auth.adapter.in.web.security.TokenAuthenticator;
import com.chzzkzzal.core.auth.adapter.out.web.TokenResolver;
import com.chzzkzzal.core.auth.application.port.out.TokenValidatorPort;
import com.chzzkzzal.core.auth.domain.TokenName;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter implements AuthenticationFilter {
	private final TokenValidatorPort tokenValidatorPort;
	private final TokenAuthenticator tokenAuthenticator;
	private final TokenResolver tokenResolver;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String token = tokenResolver
			.resolveFromCookie(request, TokenName.SESSION.name())
			.orElseThrow(MissingJwtTokenException::new);

		log.info("Extracted token: {}", token);

		tokenValidatorPort.validate(token);

		Authentication authentication = tokenAuthenticator.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.info("Authentication principal: {}", authentication.getPrincipal());
		log.info("Token validated successfully");

		filterChain.doFilter(request, response);
	}

}
