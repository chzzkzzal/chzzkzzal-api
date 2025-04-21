package com.chzzkzzal.zzal.domain.model.metadata;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.exception.metadata.MetadataFileNameNullException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

@Component
public class FileValidator {

	public void validateExtension(String fileName) {
		if (fileName == null || !fileName.contains(".")) {
			throw new MetadataFileNameNullException();
		}
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!AllowedFileExtension.isAllowed(extension)) {
			throw new MetadataUnsupportedFormatException();
		}
	}
}
