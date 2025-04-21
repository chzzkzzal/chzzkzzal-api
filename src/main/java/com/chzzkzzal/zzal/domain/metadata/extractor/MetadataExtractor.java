package com.chzzkzzal.zzal.domain.metadata.extractor;

import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.zzal.ZzalType;

public interface MetadataExtractor<T extends ZzalMetaInfo> {
	boolean supports(ZzalType type);

	T extract(MultipartFile multipartFile);

}
