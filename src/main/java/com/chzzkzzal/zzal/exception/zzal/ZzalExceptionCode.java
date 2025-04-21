package com.chzzkzzal.zzal.exception.zzal;

import org.springframework.http.HttpStatus;

import com.chzzkzzal.core.common.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ZzalExceptionCode implements ErrorCode {
	ZZAL_NOT_FOUND(HttpStatus.NOT_FOUND, "ZZAL_NOT_FOUND", "ZZ001");

	private final HttpStatus status;
	private final String message;
	private final String code;
}
