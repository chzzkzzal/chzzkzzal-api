package com.chzzkzzal.core.auth.adapter.out.web;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 역할: HTTP 요청으로부터 AccessToken, RefreshToken 추출 (Cookie)
 * resolve: 찾아내다
 */
@Component
public class TokenResolver {

	public Optional<String> resolveRefreshTokenFromRequest(HttpServletRequest request) {
		return resolveFromCookie(request, REFRESH_TOKEN.name());
	}

	public Optional<String> resolveFromCookie(HttpServletRequest request, String target) {
		Cookie[] cookies = request.getCookies();
		if (Objects.isNull(cookies)) {
			return Optional.empty();
		}

		return Arrays.stream(cookies)
			.filter(cookie -> Objects.equals(target, cookie.getName()))
			.map(Cookie::getValue)
			.findFirst();
	}
}
