package com.chzzkzzal.zzal.business.domain.zzal.metadata.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.business.application.port.in.query.ExtractMetadataQuery;
import com.chzzkzzal.zzal.business.application.port.out.MetadataExtractor;
import com.chzzkzzal.zzal.business.application.util.MultipartFileContentType;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataContentTypeNullException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MetadataQueryService {

	private final List<MetadataExtractor<? extends MediaMeta>> extractors;

	public MediaMeta extract(ExtractMetadataQuery query) {
		String contentType = query.contentType();
		if (contentType == null)
			throw new MetadataContentTypeNullException();

		MultipartFileContentType mct = MultipartFileContentType.fromString(contentType);
		ZzalType type = mct.toZzalType();

		return extractors.stream()
			.filter(e -> e.supports(type))
			.findFirst()
			.orElseThrow(MetadataUnsupportedFormatException::new)
			.extract(query.bytes(), query.originalFilename(), contentType);
	}
}
