// com/chzzkzzal/zzal/domain/exception/MetadataIOException.java
package com.chzzkzzal.zzal.exception.metadata;

import com.chzzkzzal.core.common.error.GlobalException;

public class MetadataIOException extends GlobalException {
	public MetadataIOException() {
		super(
			MetadataExceptionCode.METADATA_IO_ERROR.getMessage(),
			MetadataExceptionCode.METADATA_IO_ERROR
		);
	}

	public MetadataIOException(Throwable cause) {
		super(
			MetadataExceptionCode.METADATA_IO_ERROR.getMessage(),
			MetadataExceptionCode.METADATA_IO_ERROR
		);
		initCause(cause);
	}
}
