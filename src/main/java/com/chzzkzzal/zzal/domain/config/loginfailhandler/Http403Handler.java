package com.chzzkzzal.zzal.domain.config.loginfailhandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

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
public class Http403Handler implements AccessDeniedHandler {
	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error("[인증오류]403 접근권한이 없습니다", accessDeniedException);

		ApiResponse<?> errorResponse = ApiResponse.error(ErrorType.ACCESS_DENIED_ERROR);

		String json = objectMapper.writeValueAsString(errorResponse);
		response.getWriter().write(json);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

	}
}
