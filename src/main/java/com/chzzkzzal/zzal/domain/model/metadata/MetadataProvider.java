package com.chzzkzzal.zzal.domain.model.metadata;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.model.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.model.zzal.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataContentTypeNullException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MetadataProvider {

	private final List<MetadataExtractor<? extends ZzalMetaInfo>> extractors;

	public ZzalMetaInfo getMetadata(MultipartFile file) {
		String contentType = file.getContentType();
		if (contentType == null)
			throw new MetadataContentTypeNullException();

		MultipartFileContentType mct = MultipartFileContentType.fromString(contentType);
		ZzalType type = mct.toZzalType();

		return extractors.stream()
			.filter(e -> e.supports(type))
			.findFirst()
			.orElseThrow(MetadataUnsupportedFormatException::new)
			.extract(file);
	}
}
