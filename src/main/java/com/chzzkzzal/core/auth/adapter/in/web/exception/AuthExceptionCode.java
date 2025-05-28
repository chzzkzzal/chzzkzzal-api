package com.chzzkzzal.core.auth.adapter.in.web.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.chzzkzzal.common.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode implements ErrorCode {
	// 회원 등록 관련
	ALREADY_REGISTERED_USER(BAD_REQUEST, "AUTH_100", "이미 다른 소셜 계정으로 가입된 사용자입니다."),

	// 인증 실패 관련
	AUTHENTICATION_REQUIRED(UNAUTHORIZED, "AUTH_200", "로그인이 필요합니다."),
	MISSING_JWT_TOKEN(UNAUTHORIZED, "AUTH_201", "JWT 인증 토큰이 존재하지 않습니다."),

	// 토큰 관련
	REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "AUTH_301", "리프레시 토큰을 찾을 수 없습니다."),
	REFRESH_TOKEN_INVALID(UNAUTHORIZED, "AUTH_300", "리프레시 토큰이 유효하지 않거나 만료되었습니다."),
	INVALID_JWT_SIGNATURE(UNAUTHORIZED, "AUTH_800", "JWT 서명이 유효하지 않습니다."),

	;

	private final HttpStatus status;
	private final String code;
	private final String message;
}
