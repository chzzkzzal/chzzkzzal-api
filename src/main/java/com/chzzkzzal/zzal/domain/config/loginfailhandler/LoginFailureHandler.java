package com.chzzkzzal.zzal.domain.config.loginfailhandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.support.error.ErrorType;
import com.chzzkzzal.core.support.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		log.error("[인증오류] 아이디 혹은 비밀번호가 올바르지 않습니다.", exception);

		ApiResponse<?> errorResponse = ApiResponse.error(ErrorType.LOGIN_FAIL_ERROR);

		String json = objectMapper.writeValueAsString(errorResponse);
		response.getWriter().write(json);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

	}
}
