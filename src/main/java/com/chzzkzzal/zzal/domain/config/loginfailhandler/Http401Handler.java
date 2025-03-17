package com.chzzkzzal.zzal.domain.config.loginfailhandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.chzzkzzal.core.support.error.ErrorType;
import com.chzzkzzal.core.support.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Http401Handler implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		log.error("[인증오류]401 로그인이 필요합니다.", authException);
		ApiResponse<?> errorResponse = ApiResponse.error(ErrorType.UNAUTHORIZED_ERROR);

		String json = objectMapper.writeValueAsString(errorResponse);
		response.getWriter().write(json);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

	}
}
