package com.chzzkzzal.core.auth.application.port.out;

import java.time.Duration;

import org.springframework.http.ResponseCookie;

import com.chzzkzzal.core.auth.application.result.TokenResult;

import jakarta.servlet.http.HttpServletResponse;

public interface CookieWriterPort {
	void injectRefreshTokenToCookie(TokenResult result, HttpServletResponse response);

	void addCookie(String name, String value, Duration duration, HttpServletResponse response);

	void invalidateCookie(String name, HttpServletResponse response);

	ResponseCookie makeCookie(String name, String value, Duration duration);

}
