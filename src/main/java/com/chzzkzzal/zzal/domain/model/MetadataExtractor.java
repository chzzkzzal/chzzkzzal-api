package com.chzzkzzal.zzal.domain.model;

import org.springframework.web.multipart.MultipartFile;

public interface MetadataExtractor<T> {
	T extract(MultipartFile multipartFile);

}
