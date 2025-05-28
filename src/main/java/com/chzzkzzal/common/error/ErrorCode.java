package com.chzzkzzal.common.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
	HttpStatus getStatus();

	String getCode();

	String getMessage();
}
