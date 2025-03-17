package com.chzzkzzal.core.support.error;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public enum ErrorType {

	DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "An unexpected error has occurred.",
		LogLevel.ERROR),

	LOGIN_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E400, "Bad Request", LogLevel.ERROR),
	UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, ErrorCode.E401, "Need login", LogLevel.ERROR),

	ACCESS_DENIED_ERROR(HttpStatus.FORBIDDEN, ErrorCode.E403, "Cant Access", LogLevel.ERROR);

	private final HttpStatus status;

	private final ErrorCode code;

	private final String message;

	private final LogLevel logLevel;

	ErrorType(HttpStatus status, ErrorCode code, String message, LogLevel logLevel) {

		this.status = status;
		this.code = code;
		this.message = message;
		this.logLevel = logLevel;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public ErrorCode getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

}
