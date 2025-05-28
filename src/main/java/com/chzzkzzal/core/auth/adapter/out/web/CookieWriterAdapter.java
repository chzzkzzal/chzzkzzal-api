package com.chzzkzzal.core.auth.adapter.out.web;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.SecurityProperties;
import com.chzzkzzal.common.properties.TokenProperties;
import com.chzzkzzal.core.auth.application.port.out.CookieWriterPort;
import com.chzzkzzal.core.auth.application.result.TokenResult;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieWriterAdapter implements CookieWriterPort {
	private final TokenProperties tokenProperties;
	private final SecurityProperties securityProperties;

	@Override
	public void injectRefreshTokenToCookie(TokenResult result, HttpServletResponse response) {
		addCookie(
			REFRESH_TOKEN.name(),
			result.refreshToken(),
			Duration.ofHours(tokenProperties.expirationTime().refreshTokenHours()),
			response
		);
	}

	@Override
	public void invalidateCookie(String name, HttpServletResponse response) {
		addCookie(name, "", Duration.ZERO, response);
	}

	@Override
	public void addCookie(String name, String value, Duration duration, HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
			.path("/")
			.maxAge(duration)
			.httpOnly(securityProperties.cookie().httpOnly())
			.domain(securityProperties.cookie().domain())
			.secure(securityProperties.cookie().secure())
			.sameSite(securityProperties.cookie().sameSite())
			.build();
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	@Override
	public ResponseCookie makeCookie(String name, String value, Duration duration) {
		return ResponseCookie.from(name, value)
			.path("/")
			.sameSite(securityProperties.cookie().sameSite())
			.httpOnly(securityProperties.cookie().httpOnly())
			.secure(securityProperties.cookie().secure())
			.domain(securityProperties.cookie().domain())
			.maxAge(duration)
			.build();
	}
}
