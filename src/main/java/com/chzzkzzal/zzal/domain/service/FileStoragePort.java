package com.chzzkzzal.zzal.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {
	String storeFile(MultipartFile file);

	String loadFile(String fileName);
}
