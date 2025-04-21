package com.chzzkzzal.zzal.domain.model.metadata;

import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.model.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.model.zzal.ZzalType;

public interface MetadataExtractor<T extends ZzalMetaInfo> {
	boolean supports(ZzalType type);

	T extract(MultipartFile multipartFile);

}
