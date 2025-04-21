package com.chzzkzzal.zzal.exception.metadata;

import com.chzzkzzal.core.common.error.GlobalException;

public class MetadataFileNameNullException extends GlobalException {
	public MetadataFileNameNullException() {
		super(
			MetadataExceptionCode.METADATA_FILENAME_NULL.getMessage(),
			MetadataExceptionCode.METADATA_FILENAME_NULL
		);
	}
}
