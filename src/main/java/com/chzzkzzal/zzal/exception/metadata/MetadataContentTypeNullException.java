package com.chzzkzzal.zzal.exception.metadata;

import com.chzzkzzal.core.common.error.GlobalException;

public class MetadataContentTypeNullException extends GlobalException {
	public MetadataContentTypeNullException() {
		super(
			MetadataExceptionCode.METADATA_CONTENT_TYPE_NULL.getMessage(),
			MetadataExceptionCode.METADATA_CONTENT_TYPE_NULL
		);
	}
}
