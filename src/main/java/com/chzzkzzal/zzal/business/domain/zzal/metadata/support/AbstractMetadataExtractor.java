package com.chzzkzzal.zzal.business.domain.zzal.metadata.support;

import com.chzzkzzal.zzal.business.application.port.out.MetadataExtractor;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;

public abstract class AbstractMetadataExtractor<T extends MediaMeta>
	implements MetadataExtractor<T> {

	@Override
	public final T extract(final byte[] bytes, final String originalName, final String contentType) {

		return parseMetadata(bytes, originalName, contentType);
	}

	protected abstract T parseMetadata(byte[] bytes, final String originalName, final String contentType);
}
