package com.chzzkzzal.zzal.exception.metadata;

import com.chzzkzzal.core.common.error.GlobalException;

public class MetadataUnsupportedFormatException extends GlobalException {

	public MetadataUnsupportedFormatException() {
		super(
			MetadataExceptionCode.METADATA_UNSUPPORTED_EXTENSION.getMessage(),
			MetadataExceptionCode.METADATA_UNSUPPORTED_EXTENSION
		);
	}
}
