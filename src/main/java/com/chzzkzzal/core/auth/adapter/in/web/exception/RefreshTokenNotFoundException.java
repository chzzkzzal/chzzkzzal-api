package com.chzzkzzal.core.auth.adapter.in.web.exception;

import static com.chzzkzzal.core.auth.adapter.in.web.exception.AuthExceptionCode.*;

import com.chzzkzzal.common.error.GlobalException;

public class RefreshTokenNotFoundException extends GlobalException {

	public RefreshTokenNotFoundException() {
		super(REFRESH_TOKEN_NOT_FOUND.getMessage(), REFRESH_TOKEN_NOT_FOUND);
	}
}
