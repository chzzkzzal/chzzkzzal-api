package com.chzzkzzal.zzal.infrastructure.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.service.FileStoragePort;

@Service
public class S3Port implements FileStoragePort {
	@Override
	public String storeFile(MultipartFile file) {
		return null;
	}

	@Override
	public String loadFile(String fileName) {
		return null;
	}
}
