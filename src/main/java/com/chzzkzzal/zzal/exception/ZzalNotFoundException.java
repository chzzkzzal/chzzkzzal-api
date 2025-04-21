package com.chzzkzzal.zzal.exception;

import com.chzzkzzal.core.common.error.GlobalException;

public class ZzalNotFoundException extends GlobalException {
	public ZzalNotFoundException() {
		super(
			ZzalExceptionCode.ZZAL_NOT_FOUND.getMessage(),
			ZzalExceptionCode.ZZAL_NOT_FOUND
		);
	}
}
