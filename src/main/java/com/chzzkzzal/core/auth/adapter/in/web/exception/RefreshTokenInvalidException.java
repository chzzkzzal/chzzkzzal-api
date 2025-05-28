package com.chzzkzzal.core.auth.adapter.in.web.exception;

import static com.chzzkzzal.core.auth.adapter.in.web.exception.AuthExceptionCode.*;

import com.chzzkzzal.common.error.GlobalException;

public class RefreshTokenInvalidException extends GlobalException {
	public RefreshTokenInvalidException() {
		super(REFRESH_TOKEN_INVALID.getMessage(), REFRESH_TOKEN_INVALID);
	}
}
