package com.chzzkzzal.zzal.business.application.port.out;

import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.ZzalType;

public interface MetadataExtractor<T extends MediaMeta> {
	boolean supports(ZzalType type);

	T extract(final byte[] bytes, final String originalName, final String contentType);

}
