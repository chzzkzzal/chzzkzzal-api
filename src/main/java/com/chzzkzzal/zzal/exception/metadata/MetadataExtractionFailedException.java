// com/chzzkzzal/zzal/domain/exception/MetadataExtractionFailedException.java
package com.chzzkzzal.zzal.exception.metadata;

import com.chzzkzzal.core.common.error.GlobalException;

public class MetadataExtractionFailedException extends GlobalException {
	public MetadataExtractionFailedException() {
		super(
			MetadataExceptionCode.METADATA_EXTRACTION_FAILED.getMessage(),
			MetadataExceptionCode.METADATA_EXTRACTION_FAILED
		);
	}

	public MetadataExtractionFailedException(Throwable cause) {
		super(
			MetadataExceptionCode.METADATA_EXTRACTION_FAILED.getMessage(),
			MetadataExceptionCode.METADATA_EXTRACTION_FAILED
		);
		initCause(cause);
	}
}
