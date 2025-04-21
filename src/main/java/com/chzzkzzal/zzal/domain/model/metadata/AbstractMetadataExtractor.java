package com.chzzkzzal.zzal.domain.model.metadata;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.model.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.exception.metadata.MetadataContentTypeNullException;
import com.chzzkzzal.zzal.exception.metadata.MetadataIOException;

public abstract class AbstractMetadataExtractor<T extends ZzalMetaInfo>
	implements MetadataExtractor<T> {

	@Override
	public final T extract(MultipartFile file) {
		String contentType = file.getContentType();
		if (contentType == null)
			throw new MetadataContentTypeNullException();

		byte[] bytes = readFileBytes(file);
		return parseMetadata(bytes, file);
	}

	protected byte[] readFileBytes(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

	protected abstract T parseMetadata(byte[] bytes, MultipartFile file);
}
